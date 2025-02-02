package ru.darin.coordinates.util.exceptions;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.darin.coordinates.util.Location;
import ru.darin.coordinates.util.LocationForDistrict;

import java.util.List;

public class ExceptionBuilder {
    public static void buildErrorMessageForClient(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errorList = bindingResult.getFieldErrors();
            errorList.stream().forEach(fieldError -> errorMsg
                    .append(fieldError.getField())
                    .append(" - ").append(fieldError.getDefaultMessage())
                    .append(";"));
            throw new CoordinatesException(errorMsg.toString());
        }
    }

    public static void buildErrorMessageForClientRegionNotFound(Location[] response) {
        if (response.length == 0) {
            String errorMsg = "Не найдено области с таким названием";
            throw new CoordinatesExceptionNotFound(errorMsg);
        }
    }

    public static void buildErrorMessageForClientDistrictNotFound(LocationForDistrict[] response) {
        if (response.length == 0) {
            String errorMsg = "Не найдено федерального округа с таким названием";
            throw new CoordinatesExceptionNotFound(errorMsg);
        }
    }

}
