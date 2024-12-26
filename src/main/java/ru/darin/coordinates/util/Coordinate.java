package ru.darin.coordinates.util;

import lombok.Data;

import java.util.List;

@Data
public class Coordinate {
    List<Double>coordinatesList;

    public Coordinate(List<Double> coordinatesList) {
        this.coordinatesList = coordinatesList;
    }
}
