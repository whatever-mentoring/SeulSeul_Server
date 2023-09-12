package com.seulseul.seulseul.controller;

import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.entity.ApiKey;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
public class HelloController {

    private final ApiKey apiService;
    private final BaseRouteService baseRouteService;

    public HelloController(ApiKey apiService, BaseRouteService baseRouteService) {
        this.apiService = apiService;
        this.baseRouteService = baseRouteService;
    }

    @GetMapping("/recent")
    public Mono<BaseRouteDto> getResponseTest() throws IOException {
        return baseRouteService.getResponseTest();
    }

    @GetMapping("/test")
    public String Test() {
        return "test";
    }
}
