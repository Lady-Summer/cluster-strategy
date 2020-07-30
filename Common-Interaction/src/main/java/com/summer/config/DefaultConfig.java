package com.summer.config;

import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:config/default.properties")
public class DefaultConfig {

    public String accessKey;

    public String regionId;

    public String accessSecret;

    public String securityGroupId;

    public String keyPairName;
}
