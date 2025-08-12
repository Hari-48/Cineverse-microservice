package com.hari.tamil_movies.exceptionHandling;

public class ErrorResponse {
    private String message;
    private int statusCode;

    public long getTimestamp() {
        return timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    private long timestamp;

    // constructors, getters, setters
    public ErrorResponse(String message, int statusCode, long timestamp) {
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
    }
}
