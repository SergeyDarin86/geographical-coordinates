package ru.darin.coordinates.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SearchDTOForDistrict {
    @NotEmpty(message = "не может быть пустым полем")
    @Pattern(regexp = "[а-яё]+", message = "Название региона должно соответствовать следующему формату: 'пфо'")
    private String location;
}
