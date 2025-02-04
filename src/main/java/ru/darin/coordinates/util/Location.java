package ru.darin.coordinates.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Location {

    private long place_id;

    @JsonProperty("licence")
    private String licence;

    private String name;

    @JsonProperty("geojson")
    private GeoJson geoJson;

    public Location(long place_id, String licence, String name, GeoJson geoJson) {
        this.place_id = place_id;
        this.licence = licence;
        this.name = name;
        this.geoJson = geoJson;
    }
}
