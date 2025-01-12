package ru.darin.coordinates.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.darin.coordinates.util.CoordinatesForGeoCenter;
import ru.darin.coordinates.util.CoordinateResponse;
import ru.darin.coordinates.util.Location;
import ru.darin.coordinates.util.LocationForDistrict;

import java.util.List;

@Service
public class CoordinatesService {

    private final RestTemplate restTemplate;

    public CoordinatesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("cacheForRegion")
    public CoordinateResponse getCoordinatesForRegion(String url) {

        Location[] response = restTemplate.getForObject(url, Location[].class);

        int countOfCoordinates = response[0].getGeoJson().getCoordinates().get(0).size();
        System.out.println(countOfCoordinates + ": count of coordinates");

        double longitude = 0;
        double latitude = 0;
        for (List<Float> doubles : response[0].getGeoJson().getCoordinates().get(0)) {
            longitude += doubles.get(0);
            latitude += doubles.get(1);
        }

        double centerForLongitude = longitude / countOfCoordinates;
        double centerForLatitude = latitude / countOfCoordinates;
        System.out.println(centerForLongitude + " <- longitude");
        System.out.println(centerForLatitude + " <- latitude");

        return new CoordinateResponse(response[0].getName(), new CoordinatesForGeoCenter(centerForLatitude, centerForLongitude));
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

        return new CoordinateResponse(response[0].getName(), new CoordinatesForGeoCenter(centerForLatitude, centerForLongitude));
    }

}
