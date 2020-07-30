package com.summer.enums.configType;

public enum InternetChargeType {

    PayByBandwidth("PayByBandwidth", "固定带宽"),

    PayByTraffic("PayByTraffic", "按流量付费");

    String mode;

    String description;

    InternetChargeType(String mode, String description) {
        this.mode = mode;
        this.description = description;
    }
}
