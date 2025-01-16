package ru.darin.coordinates.util.exceptions;

import lombok.Data;

@Data
public class CoordinatesErrorResponse {
    private String message;
    private long timestamp;

    public CoordinatesErrorResponse(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }
}
