package ru.darin.coordinates.util;

import lombok.Data;

import java.util.List;

@Data
public class GeoJson {
    private String type;

    private List<List<List<Float>>> coordinates;

    public GeoJson(String type) {
        this.type = type;
    }
}
