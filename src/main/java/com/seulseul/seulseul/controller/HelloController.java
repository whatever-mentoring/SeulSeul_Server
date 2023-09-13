package com.seulseul.seulseul.controller;

import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.entity.ApiKey;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/hello")
    public ResponseEntity<BaseRouteDto> getStationID() throws IOException {
        return new ResponseEntity(baseRouteService.getStationID(), HttpStatus.OK);
    }

    @GetMapping("/test")
    public String Test() {
        return "test";
    }
}
