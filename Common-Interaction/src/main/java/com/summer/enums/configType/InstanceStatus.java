package com.summer.enums.configType;

public enum InstanceStatus {

    RUNNING(0, "Running"),

    STARTING(1, "Starting"),

    STOPPING(2, "Stopping"),

    STOPPED(3, "Stopped");

    int statusCode;

    String description;

    InstanceStatus(int statusCode, String description) {
        this.description = description;
        this.statusCode = statusCode;
    }

}
