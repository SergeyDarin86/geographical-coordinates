package ru.darin.coordinates.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.darin.coordinates.dto.SearchDTOForDistrict;

public class CustomValidatorForDistrict implements ConstraintValidator<ValidSearchDTO, SearchDTOForDistrict> {
    @Override
    public boolean isValid(SearchDTOForDistrict dto, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (dto.getLocation() == null || dto.getLocation().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Поле не может быть пустым")
                    .addPropertyNode("location")
                    .addConstraintViolation();
            isValid = false;
        } else if (!dto.getLocation().matches("[а-яё]+")) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Название региона должно соответствовать следующему формату: 'пфо'")
                    .addPropertyNode("location").addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }
}
