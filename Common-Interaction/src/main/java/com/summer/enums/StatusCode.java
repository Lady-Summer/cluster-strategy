package com.summer.enums;

public enum StatusCode {

    PENDING(0, "pending"),
    RUNNING(1, "running"),
    STOPPING(2, "stopping"),
    STOPPED(-1, "stopped"),
    UNHEALTHY(3, "unhealthy");

    private int statusCode;

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    private String statusMessage;

    StatusCode(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
