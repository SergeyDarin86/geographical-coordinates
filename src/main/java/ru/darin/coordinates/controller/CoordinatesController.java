package ru.darin.coordinates.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.darin.coordinates.service.CoordinatesService;
import ru.darin.coordinates.util.Location;

import java.util.List;

@RestController
public class CoordinatesController {

    private final CoordinatesService service;

    public CoordinatesController(CoordinatesService service) {
        this.service = service;
    }

    @GetMapping("/getGeoCenter")
    public List<Location> getGeoCenterForRegion(@RequestParam(value = "location", defaultValue = "") String location){
        String url = "https://nominatim.openstreetmap.org/search?state=" + location + "&country=russia&format=json&polygon_geojson=1";
        return service.getResponse(url);
    }

}
