package com.summer.enums.configType;

public enum CloudType {

    ALICLOUD(1, "AliCloud"),
    TENCENTCLOUD(2, "TencentCloud");

    private int code;

    private String description;

    CloudType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
