package com.luckyvicky.dndbackend.hn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "api")
public class OdsayProperties {

    private String apiKey;
    private String url;
}
