package ru.darin.coordinates.util.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Класс, возвращающий текст ошибки пользователю в случае некорректно введенных данных.")
public class CoordinatesErrorResponse {
    @Schema(
            description = "Текст сообщения об ошибке",
            example = "Название региона должно соответствовать следующему формату: 'Нижегородская область' либо 'Нижегородская обл'"
    )
    private String message;

    @Schema(description = "Временная метка (timestamp)", example = "1729178966070")
    private long timestamp;

    public CoordinatesErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
