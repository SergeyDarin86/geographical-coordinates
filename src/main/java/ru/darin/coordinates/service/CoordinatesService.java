package ru.darin.coordinates.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.darin.coordinates.util.Location;

import java.util.Arrays;
import java.util.List;

@Service
public class CoordinatesService {

    private final RestTemplate restTemplate;

    public CoordinatesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("myCache")
    public List<Location> getResponse(String url) {

        Location[] response = restTemplate.getForObject(url, Location[].class);

        int countOfCoordinates = response[0].getGeoJson().getCoordinates().get(0).get(0).size();
        System.out.println(countOfCoordinates + ": count of coordinates");
        System.out.println(response[0].getGeoJson().getCoordinates().get(0).get(0).get(6608));

        double longitude = 0;
        double latitude = 0;
        for (List<Float>doubles : response[0].getGeoJson().getCoordinates().get(0).get(0)){
            longitude += doubles.get(0);
            latitude += doubles.get(1);
        }

        double centerForLongitude = longitude / countOfCoordinates;
        double centerForLatitude = latitude / countOfCoordinates;
        System.out.println(centerForLongitude + " <- longitude");
        System.out.println(centerForLatitude + " <- latitude");

        return Arrays.asList(response);
    }

}
