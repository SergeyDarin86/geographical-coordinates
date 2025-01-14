package ru.darin.coordinates.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.darin.coordinates.service.CoordinatesService;
import ru.darin.coordinates.util.CoordinateResponse;

@RestController
public class CoordinatesController {

    //TODO:
    // 1) добавить БД в проект (возможно будет одна таблица)
    // 2) сделать DTO + Mapper
    // 3) сделать валидацию вводимых данных (только строки)
    // 4) добавить ExceptionHandler (такого региона нет; форма для ввода не должна быть пустой)
    // 5) запуск в докере
    // 6) тестирование
    // 7) документация
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
