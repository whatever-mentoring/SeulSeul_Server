package com.seulseul.seulseul.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

;

@Component
@RequiredArgsConstructor
@Getter
@PropertySource(value = "classpath:application-API-KEY.properties")
public class ApiKey {
    @Value("${ODSAY-API-KEY}")
    private String apiKey;
}
