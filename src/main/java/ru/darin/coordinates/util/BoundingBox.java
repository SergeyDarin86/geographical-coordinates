package ru.darin.coordinates.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class BoundingBox {
//    private List<String> boundingBox;
//
//    public BoundingBox(List<String> boundingBox) {
//        this.boundingBox = boundingBox;
//    }

//    @JsonProperty("boundingBox")
    private List<Double> boundingBox;

    public BoundingBox(List<Double> boundingBox) {
        this.boundingBox = boundingBox;
    }

}
