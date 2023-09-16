package com.seulseul.seulseul.controller;

import com.seulseul.seulseul.entity.ApiKey;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
public class HelloController {

    private final ApiKey apiService;
    private final BaseRouteService baseRouteService;

    public HelloController(ApiKey apiService, BaseRouteService baseRouteService) {
        this.apiService = apiService;
        this.baseRouteService = baseRouteService;
    }
/*
    @GetMapping("/hello")
    public ResponseEntity<BaseRouteDto> getStationID() throws IOException {
        return new ResponseEntity(baseRouteService.getStationID(), HttpStatus.OK);
    }*/


}
