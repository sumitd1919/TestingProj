package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(value = "aws")
@EnableConfigurationProperties
public class AwsConfig {

    private String endPointUrl;
    private String bucket;
    private String accessKey;
    private String secretKey;
}
