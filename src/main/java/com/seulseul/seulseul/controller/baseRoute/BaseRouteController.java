package com.seulseul.seulseul.controller.baseRoute;

import com.seulseul.seulseul.dto.Response.ResponseData;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteStartDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteStartReqDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteStartUpdateDto;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.BatchUpdateException;
import java.util.Optional;

@RestController
public class BaseRouteController {
    private final BaseRouteService baseRouteService;

    public BaseRouteController(BaseRouteService baseRouteService) {
        this.baseRouteService = baseRouteService;
    }

    @PostMapping("/v1/start")
    public ResponseEntity<ResponseData> saveStartInfo(@RequestBody BaseRouteStartReqDto dto) {
        BaseRouteStartDto reqDto = baseRouteService.saveStartInfo(dto);
        ResponseData responseData = new ResponseData(200, reqDto);
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }

    @PatchMapping("/v1/start")
    public ResponseEntity<ResponseData> updateStartInfo(@RequestBody BaseRouteStartUpdateDto dto) {
        BaseRouteStartDto startDto = baseRouteService.updateStartInfo(dto);
        ResponseData responseData = new ResponseData(200, startDto);
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }

    // 임시 endpoint
    @GetMapping("/v1/start/info/{id}")
    public ResponseEntity<ResponseData> getStationIdAndName(@PathVariable Long id) throws IOException {
        Optional<BaseRoute> baseRoute = baseRouteService.getStationIdAndName(id);
        ResponseData responseData = new ResponseData(200, baseRoute);
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }
}