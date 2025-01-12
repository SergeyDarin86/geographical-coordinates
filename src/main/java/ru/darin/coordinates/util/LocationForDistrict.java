package ru.darin.coordinates.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LocationForDistrict {

    private long place_id;

    @JsonProperty("licence")
    private String licence;

    private String name;

    @JsonProperty("geojson")
    private GeoJsonForDistrict geoJsonForDistrict;

    public LocationForDistrict(long place_id, String licence, String name, GeoJsonForDistrict geoJsonForDistrict) {
        this.place_id = place_id;
        this.licence = licence;
        this.name = name;
        this.geoJsonForDistrict = geoJsonForDistrict;
    }

}
