package ru.darin.coordinates.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.darin.coordinates.dto.SearchDTOForRegion;
import ru.darin.coordinates.dto.SearchDTOForDistrict;
import ru.darin.coordinates.service.CoordinatesService;
import ru.darin.coordinates.util.CoordinateResponse;
import ru.darin.coordinates.util.exceptions.CoordinatesErrorResponse;
import ru.darin.coordinates.util.exceptions.CoordinatesException;
import ru.darin.coordinates.util.exceptions.ExceptionBuilder;
import ru.darin.coordinates.util.exceptions.CoordinatesExceptionNotFound;

@Slf4j
@RestController
public class CoordinatesController {

    //TODO:
    // + 1)  добавить БД в проект (возможно будет одна таблица)
    // 2) сделать DTO + Mapper
    // +3) сделать валидацию вводимых данных (только строки)
    // !
    // +3.1 - сделать свой кастомный валидатор, т.к. если в поле региона ничего не ввести будет две ошибки - NotEmpty() и Pattern()
    // !
    // 4)+ добавить ExceptionHandler (такого региона нет; форма для ввода не должна быть пустой)
    // 4.1)+ ExceptionHandler для федеральных округов
    // 5) запуск в докере
    // 6) тестирование
    // 7) документация
    // 8)+ логгирование
    // 9)+ Посмотреть, возможно у меня неправильный подсчет центра для округов -
    // может там нужно считать количество "кусочков" и для каждого такого куска высчитывать центр
    // и далее уже общий
    private final CoordinatesService service;

    public CoordinatesController(CoordinatesService service) {
        this.service = service;
    }

    @GetMapping("/getGeoCenter")
    public ResponseEntity getGeoCenterForRegion(@RequestBody @Valid SearchDTOForRegion searchDTOForRegion, BindingResult bindingResult){
        log.info("Start method  getGeoCenterForRegion(searchDTOForRegion) for CoordinatesController, location is: {} ", searchDTOForRegion.getLocation());
        ExceptionBuilder.buildErrorMessageForClient(bindingResult);
        String url = "https://nominatim.openstreetmap.org/search?state=" + searchDTOForRegion.getLocation() + "&country=russia&format=json&polygon_geojson=1";
        ExceptionBuilder.buildErrorMessageForClientRegionNotFound(service.getResponseForRegion(url));
        return ResponseEntity.ok(service.getCoordinatesForRegion(url));
    }

    @GetMapping("/getGeoCenterForDistrict")
    public CoordinateResponse getGeoCenterForDistrict(@RequestBody @Valid SearchDTOForDistrict searchDTO, BindingResult bindingResult){
        log.info("Start method  getGeoCenterForDistrict(searchDTO) for CoordinatesController, location is: {} ", searchDTO.getLocation());
        ExceptionBuilder.buildErrorMessageForClient(bindingResult);
        String url = "https://nominatim.openstreetmap.org/search?state=" + searchDTO.getLocation() + "&country=russia&format=json&polygon_geojson=1";
        ExceptionBuilder.buildErrorMessageForClientDistrictNotFound(service.getResponseForDistrict(url));
        return service.getCoordinatesForDistrict(url);
    }

    @ExceptionHandler
    private ResponseEntity<CoordinatesErrorResponse> handlerException(CoordinatesException e) {
        CoordinatesErrorResponse response = new CoordinatesErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        log.error("Finish method handlerException() for CoordinatesController, statusCode is: {}, message is: {} ", HttpStatus.BAD_REQUEST, response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<CoordinatesErrorResponse> coordinatesHandlerException(CoordinatesExceptionNotFound e) {
        CoordinatesErrorResponse response = new CoordinatesErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        log.error("Finish method coordinatesHandlerException() for CoordinatesController, statusCode is: {}, message is: {} ", HttpStatus.NOT_FOUND, response.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
