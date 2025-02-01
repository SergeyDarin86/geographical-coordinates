package ru.darin.coordinates.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Класс для вывода координат географического центра.")
public class CoordinatesForGeoCenter {
    @Schema(description = "Широта", example = "54.58687145374439")
    private Double latitude;

    @Schema(description = "Долгота", example = "50.58003656541696")
    private Double longitude;

    public CoordinatesForGeoCenter(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
