package ru.darin.coordinates.util;

import lombok.Data;

@Data
public class CoordinateResponse {
    private String name;
    private CoordinatesForGeoCenter coordinatesForGeoCenter;

    public CoordinateResponse(String name, CoordinatesForGeoCenter coordinatesForGeoCenter) {
        this.name = name;
        this.coordinatesForGeoCenter = coordinatesForGeoCenter;
    }
}
