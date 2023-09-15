package com.seulseul.seulseul.controller.baseRoute;

import com.seulseul.seulseul.dto.Response.ResponseData;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteStartDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteStartReqDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteStartUpdateDto;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.BatchUpdateException;

@RestController
public class BaseRouteController {
    private final BaseRouteService baseRouteService;

    public BaseRouteController(BaseRouteService baseRouteService) {
        this.baseRouteService = baseRouteService;
    }

    @PostMapping("/v1/start")
    public ResponseEntity<ResponseData> saveStartInfo(@RequestBody BaseRouteStartReqDto dto) throws IOException {
        BaseRouteStartDto reqDto = baseRouteService.saveStartInfo(dto);
        ResponseData responseData = new ResponseData(200, reqDto);
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }

    @PatchMapping("/v1/start")
    public ResponseEntity<ResponseData> updateStartInfo(@RequestBody BaseRouteStartUpdateDto dto) throws IOException {
        BaseRouteStartDto startDto = baseRouteService.updateStartInfo(dto);
        ResponseData responseData = new ResponseData(200, startDto);
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }
}