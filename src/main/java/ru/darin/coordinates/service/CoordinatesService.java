package ru.darin.coordinates.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.darin.coordinates.model.Region;
import ru.darin.coordinates.repository.RegionRepository;
import ru.darin.coordinates.util.CoordinateResponse;
import ru.darin.coordinates.util.CoordinatesForGeoCenter;
import ru.darin.coordinates.util.Location;
import ru.darin.coordinates.util.LocationForDistrict;

import java.util.List;

@Service
public class CoordinatesService {

    private final RestTemplate restTemplate;
    private final RegionRepository repository;

    public CoordinatesService(RestTemplate restTemplate, RegionRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    public Location[] getResponse(String url) {
        return restTemplate.getForObject(url, Location[].class);
    }

    @Cacheable("cacheForRegion")
    public CoordinateResponse getCoordinatesForRegion(String url) {
        int countOfCoordinates = getResponse(url)[0].getGeoJson().getCoordinates().get(0).size();
        double longitude = 0;
        double latitude = 0;
        for (List<Float> doubles : getResponse(url)[0].getGeoJson().getCoordinates().get(0)) {
            longitude += doubles.get(0);
            latitude += doubles.get(1);
        }

        double centerForLongitude = longitude / countOfCoordinates;
        double centerForLatitude = latitude / countOfCoordinates;
        System.out.println(centerForLongitude + " <- longitude");
        System.out.println(centerForLatitude + " <- latitude");
        saveCoordinates(getResponse(url)[0].getName(), centerForLongitude, centerForLatitude);

        return new CoordinateResponse(getResponse(url)[0].getName(), new CoordinatesForGeoCenter(centerForLatitude, centerForLongitude));
    }

    @Cacheable("cacheForDistrict")
    public CoordinateResponse getCoordinatesForDistrict(String url) {

        LocationForDistrict[] response = restTemplate.getForObject(url, LocationForDistrict[].class);
        int countOfCoordinates = response[0].getGeoJsonForDistrict().getCoordinatesForDistrict().get(0).get(0).size();

        double longitude = 0;
        double latitude = 0;

        for (List<Float> doubles : response[0].getGeoJsonForDistrict().getCoordinatesForDistrict().get(0).get(0)) {
            longitude += doubles.get(0);
            latitude += doubles.get(1);
        }

//        for (List<Float>floats : response[0].getGeoJsonForDistrict().getCoordinatesForDistrict().get(0).get(0)){
//            System.out.println(floats);
//        }
        int countOfParts = response[0].getGeoJsonForDistrict().getCoordinatesForDistrict().size();

        System.out.println("===============");
        System.out.println(longitude + ": lon");
        System.out.println(latitude + ": lat");
        System.out.println(countOfParts + ": count of parts");
        System.out.println("===============");

        double centerForLongitude = longitude / countOfCoordinates;
        double centerForLatitude = latitude / countOfCoordinates;
        saveCoordinates(response[0].getName(), centerForLongitude, centerForLatitude);

        return new CoordinateResponse(response[0].getName(), new CoordinatesForGeoCenter(centerForLatitude, centerForLongitude));
    }

    public void saveCoordinates(String regionName, double longitude, double latitude) {
        Region region = new Region();
        region.setRegionName(regionName);
        region.setLongitude(longitude);
        region.setLatitude(latitude);
        if (repository.findRegionByRegionName(regionName).isEmpty())
            repository.save(region);
    }

}
