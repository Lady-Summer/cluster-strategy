package com.summer.enums.configType;

public enum DiskType {

    CLOUD_EFFICIENCY("cloud_efficiency", "高效云盘"),

    CLOUD_SSD("cloud_ssd", "SSD云盘"),

    CLOUD("cloud", "普通云盘"),

    EPHEMERAL_SSD("ephemeral_ssd", "本地SSD盘"),

    CLOUD_ESSD("cloud_essd", "ESSD云盘");

    String type;

    String description;

    DiskType(String type, String description) {
        this.type = type;
        this.description = description;
    }
}
