package ru.darin.coordinates.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Location {

    private long place_id;

    @JsonProperty("licence")
    private String licence;

    private String name;

    private List<String> boundingBox;

    @JsonProperty("geojson")
    private GeoJson geoJson;

    public Location(long place_id, String licence, String name, List<String> boundingBox, GeoJson geoJson) {
        this.place_id = place_id;
        this.licence = licence;
        this.name = name;
        this.boundingBox = boundingBox;
        this.geoJson = geoJson;
    }
}
