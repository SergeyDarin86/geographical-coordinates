package ru.darin.coordinates.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.darin.coordinates.service.CoordinatesService;
import ru.darin.coordinates.util.CoordinateResponse;

@RestController
public class CoordinatesController {

    private final CoordinatesService service;

    public CoordinatesController(CoordinatesService service) {
        this.service = service;
    }

    @GetMapping("/getGeoCenter")
    public CoordinateResponse getGeoCenterForRegion(@RequestParam(value = "location", defaultValue = "") String location){
        String url = "https://nominatim.openstreetmap.org/search?state=" + location + "&country=russia&format=json&polygon_geojson=1";
        return service.getCoordinatesForRegion(url);
    }

    @GetMapping("/getGeoCenterForDistrict")
    public CoordinateResponse getGeoCenterForDistrict(@RequestParam(value = "location", defaultValue = "") String location){
        String url = "https://nominatim.openstreetmap.org/search?state=" + location + "&country=russia&format=json&polygon_geojson=1";
        return service.getCoordinatesForDistrict(url);
    }

}
