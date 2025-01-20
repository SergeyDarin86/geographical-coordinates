package ru.darin.coordinates.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.darin.coordinates.dto.SearchDTOForRegion;

public class CustomValidatorForRegion implements ConstraintValidator<ValidSearchDTOForRegion, SearchDTOForRegion> {
    @Override
    public boolean isValid(SearchDTOForRegion dto, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (dto.getLocation() == null || dto.getLocation().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Поле не может быть пустым")
                    .addPropertyNode("location")
                    .addConstraintViolation();
            isValid = false;
        } else if (!dto.getLocation().matches("[А-ЯЁ]?[а-яё]+ [а-яё]+")) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Название региона должно соответствовать следующему формату: 'Нижегородская область' либо 'Нижегородская обл'")
                    .addPropertyNode("location").addConstraintViolation();
            isValid = false;
        }
        return isValid;
    }
}
