package ru.darin.coordinates.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.darin.coordinates.dto.SearchDTO;
import ru.darin.coordinates.service.CoordinatesService;
import ru.darin.coordinates.util.CoordinateResponse;
import ru.darin.coordinates.util.exceptions.CoordinatesErrorResponse;
import ru.darin.coordinates.util.exceptions.CoordinatesException;
import ru.darin.coordinates.util.exceptions.ExceptionBuilder;
import ru.darin.coordinates.util.exceptions.CoordinatesExceptionNotFound;

@RestController
public class CoordinatesController {

    //TODO:
    // + 1)  добавить БД в проект (возможно будет одна таблица)
    // 2) сделать DTO + Mapper
    // 3) сделать валидацию вводимых данных (только строки)
    // 4) добавить ExceptionHandler (такого региона нет; форма для ввода не должна быть пустой)
    // 5) запуск в докере
    // 6) тестирование
    // 7) документация
    // 8) логгирование
    private final CoordinatesService service;

    public CoordinatesController(CoordinatesService service) {
        this.service = service;
    }

    @GetMapping("/getGeoCenter")
    public ResponseEntity getGeoCenterForRegion(@RequestBody @Valid SearchDTO searchDTO, BindingResult bindingResult){
        ExceptionBuilder.buildErrorMessageForClient(bindingResult);
        String url = "https://nominatim.openstreetmap.org/search?state=" + searchDTO.getLocation() + "&country=russia&format=json&polygon_geojson=1";
        ExceptionBuilder.buildErrorMessageForClientRegionNotFound(service.getResponse(url));
        return ResponseEntity.ok(service.getCoordinatesForRegion(url));
    }

    @GetMapping("/getGeoCenterForDistrict")
    public CoordinateResponse getGeoCenterForDistrict(@RequestParam(value = "location", defaultValue = "") String location){
        String url = "https://nominatim.openstreetmap.org/search?state=" + location + "&country=russia&format=json&polygon_geojson=1";
        return service.getCoordinatesForDistrict(url);
    }

    @ExceptionHandler
    private ResponseEntity<CoordinatesErrorResponse> handlerException(CoordinatesException e) {
        CoordinatesErrorResponse response = new CoordinatesErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<CoordinatesErrorResponse> libraryHandlerException(CoordinatesExceptionNotFound e) {
        CoordinatesErrorResponse response = new CoordinatesErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
