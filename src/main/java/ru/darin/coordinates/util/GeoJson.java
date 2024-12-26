package ru.darin.coordinates.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GeoJson {

//    @JsonProperty("type")
    private String type;

//    @JsonProperty("coordinates")
    private List<List<List<List<Float>>>> coordinates;

    public GeoJson(String type) {
        this.type = type;
    }
}
