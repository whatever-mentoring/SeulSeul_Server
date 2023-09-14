package com.seulseul.seulseul.controller;

import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteStartReqDto;
import com.seulseul.seulseul.dto.test.TestDto;
import com.seulseul.seulseul.dto.transferInfo.TransferInfoDto;
import com.seulseul.seulseul.entity.ApiKey;
import com.seulseul.seulseul.entity.test.Test;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import com.seulseul.seulseul.service.test.TestService;
import com.seulseul.seulseul.service.transferInfo.TransferInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HelloController {

    private final ApiKey apiService;
    private final BaseRouteService baseRouteService;
    private final TransferInfoService transferInfoService;
    private final TestService testService;

    public HelloController(ApiKey apiService, BaseRouteService baseRouteService, TransferInfoService transferInfoService, TestService testService) {
        this.apiService = apiService;
        this.baseRouteService = baseRouteService;
        this.transferInfoService = transferInfoService;
        this.testService = testService;
    }
/*
    @GetMapping("/hello")
    public ResponseEntity<BaseRouteDto> getStationID() throws IOException {
        return new ResponseEntity(baseRouteService.getStationID(), HttpStatus.OK);
    }*/

    @GetMapping("/trans")
    public ResponseEntity<TransferInfoDto> getTransferInfo() throws IOException {
        return new ResponseEntity<>(transferInfoService.getTransferInfo(), HttpStatus.OK);
    }

    @PostMapping("/v1/test")
    public ResponseEntity<TestDto> saveDto(@RequestBody TestDto dto) {
        testService.save(dto);
        return new ResponseEntity<TestDto>(dto, HttpStatus.OK);
    }
}