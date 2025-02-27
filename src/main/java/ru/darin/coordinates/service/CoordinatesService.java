package ru.darin.coordinates.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.darin.coordinates.model.Region;
import ru.darin.coordinates.repository.RegionRepository;
import ru.darin.coordinates.util.CoordinateResponse;
import ru.darin.coordinates.util.CoordinatesForGeoCenter;
import ru.darin.coordinates.util.Location;
import ru.darin.coordinates.util.LocationForDistrict;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CoordinatesService {

    private final RestTemplate restTemplate;
    private final RegionRepository repository;

    public CoordinatesService(RestTemplate restTemplate, RegionRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    public Location[] getResponseForRegion(String url) {
        return restTemplate.getForObject(url, Location[].class);
    }

    public LocationForDistrict[] getResponseForDistrict(String url) {
        return restTemplate.getForObject(url, LocationForDistrict[].class);
    }

    @Cacheable("cacheForRegion")
    public CoordinateResponse getCoordinatesForRegion(String url) {
        log.info("Start method getCoordinatesForRegion(url) for CoordinatesService, url is: {} ", url);
        Location[] response = getResponseForRegion(url);
        int countOfCoordinates = response[0].getGeoJson().getCoordinates().get(0).size();
        double longitude = 0;
        double latitude = 0;

        for (List<Float> doubles : response[0].getGeoJson().getCoordinates().get(0)) {
            longitude += doubles.get(0);
            latitude += doubles.get(1);
        }

        double centerForLongitude = longitude / countOfCoordinates;
        double centerForLatitude = latitude / countOfCoordinates;
        saveCoordinates(response[0].getName(), centerForLongitude, centerForLatitude);
        log.info("Finish method getCoordinatesForRegion(url) for CoordinatesService, regionName is: {} ", response[0].getName());

        return new CoordinateResponse(response[0].getName(), new CoordinatesForGeoCenter(centerForLatitude, centerForLongitude));
    }

    @Cacheable("cacheForDistrict")
    public CoordinateResponse getCoordinatesForDistrict(String url) {
        log.info("Start method getCoordinatesForDistrict(url) for CoordinatesService, url is: {} ", url);
        double longitude = 0;
        double latitude = 0;

        LocationForDistrict[] response = getResponseForDistrict(url);
        List<List<Float>> maxPartOfDistrict = getMaxPartOfDistrict(response);
        int countOfCoordinates = maxPartOfDistrict.size();

        for (List<Float> list : maxPartOfDistrict) {
            longitude += list.get(0);
            latitude += list.get(1);
        }

        double centerForLongitude = longitude / countOfCoordinates;
        double centerForLatitude = latitude / countOfCoordinates;
        saveCoordinates(response[0].getName(), centerForLongitude, centerForLatitude);
        log.info("Finish method getCoordinatesForDistrict(url) for CoordinatesService, districtName is: {} ", response[0].getName());

        return new CoordinateResponse(response[0].getName(), new CoordinatesForGeoCenter(centerForLatitude, centerForLongitude));
    }

    public List<List<Float>> getMaxPartOfDistrict(LocationForDistrict[] response) {
        List<List<Float>> maxPartOfDistrict = new ArrayList<>();
        for (List<List<List<Float>>> floats : response[0].getGeoJsonForDistrict().getCoordinatesForDistrict()) {
            if (maxPartOfDistrict.size() > floats.get(0).size()) continue;
            maxPartOfDistrict = floats.get(0);
        }
        log.info("Finish method getMaxPartOfDistrict() for CoordinatesService, size of max part for District is: {} ", maxPartOfDistrict.size());
        return maxPartOfDistrict;
    }

    public void saveCoordinates(String regionName, double longitude, double latitude) {
        log.info("Start method saveCoordinates(regionName, longitude, latitude) for CoordinatesService, regionName is: {}, longitude is: {}, latitude is: {} ", regionName, longitude, latitude);
        Region region = new Region();
        region.setRegionName(regionName);
        region.setLongitude(longitude);
        region.setLatitude(latitude);
        if (repository.findRegionByRegionName(regionName).isEmpty())
            repository.save(region);
    }

}