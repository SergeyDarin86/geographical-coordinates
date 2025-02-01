package ru.darin.coordinates.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Класс для вывода результатов поиска.")
public class CoordinateResponse {
    @Schema(description = "Название региона", example = "Тульская область")
    private String name;
    @Schema(description = "Координаты географического центра")
    private CoordinatesForGeoCenter coordinatesForGeoCenter;

    public CoordinateResponse(String name, CoordinatesForGeoCenter coordinatesForGeoCenter) {
        this.name = name;
        this.coordinatesForGeoCenter = coordinatesForGeoCenter;
    }
}
