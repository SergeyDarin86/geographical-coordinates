package ru.darin.coordinates.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.darin.coordinates.service.CoordinatesService;
import ru.darin.coordinates.util.ArrayResponse;
import ru.darin.coordinates.util.Location;

import java.util.List;

@RestController
public class CoordinatesController {

    private final CoordinatesService service;

    public CoordinatesController(CoordinatesService service) {
        this.service = service;
    }

    @GetMapping("/sayHello")
    public List<Location> sayHello(){
//        search.php?state=Самарская+область&country=russia&format=jsonv2
        String region = "Самарская область";
        String url = "https://nominatim.openstreetmap.org/search?state=" + region + "&country=russia&format=json&polygon_geojson=1";

//        String url = "https://nominatim.openstreetmap.org/search.php?state=" + region + "&country=russia&format=jsonv2";
        return service.getResponse(url);
    }

}
