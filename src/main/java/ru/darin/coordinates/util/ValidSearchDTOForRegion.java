package ru.darin.coordinates.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CustomValidatorForRegion.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSearchDTOForRegion {
    String message() default "Ошибка валидации";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
