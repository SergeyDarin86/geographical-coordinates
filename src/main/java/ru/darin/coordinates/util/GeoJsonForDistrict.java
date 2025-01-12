package ru.darin.coordinates.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GeoJsonForDistrict {
    private String type;

    @JsonProperty("coordinates")
    private List<List<List<List<Float>>>> coordinatesForDistrict;

    public GeoJsonForDistrict(String type) {
        this.type = type;
    }
}
