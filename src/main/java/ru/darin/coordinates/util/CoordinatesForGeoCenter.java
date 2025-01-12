package ru.darin.coordinates.util;

import lombok.Data;

@Data
public class CoordinatesForGeoCenter {
    private Double latitude;
    private Double longitude;

    public CoordinatesForGeoCenter(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
