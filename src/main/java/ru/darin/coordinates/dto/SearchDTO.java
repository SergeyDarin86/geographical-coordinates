package ru.darin.coordinates.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SearchDTO {
    @NotEmpty(message = "не может быть пустым полем")
    @Pattern(regexp = "[А-ЯЁ]?[а-яё]+ [а-яё]+",
            message = "Название региона должно соответствовать следующему формату: 'Нижегородская область' либо 'Нижегородская обл'")
    private String location;
}
