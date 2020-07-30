package com.summer.enums.configType;

public enum InstanceChargeType {

    // 按量付费
    PostPaid( "PostPaid", "按量付费"),

    // 包年包月
    PrePaid( "PrePaid", "包年包月");

    String mode;

    String description;

    InstanceChargeType(String mode, String desc) {
        this.mode = mode;
        this.description = desc;
    }
}
