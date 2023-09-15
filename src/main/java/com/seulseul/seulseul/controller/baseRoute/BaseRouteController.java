package com.seulseul.seulseul.controller.baseRoute;

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
    public ResponseEntity<BaseRouteStartDto> saveStartInfo(@RequestBody BaseRouteStartReqDto dto) throws IOException {
        BaseRouteStartDto reqDto = baseRouteService.saveStartInfo(dto);
        return new ResponseEntity<BaseRouteStartDto>(reqDto, HttpStatus.OK);
    }

    @PatchMapping("/v1/start")
    public ResponseEntity<BaseRouteStartDto> updateStartInfo(@RequestBody BaseRouteStartUpdateDto dto) throws IOException {
        BaseRouteStartDto startDto = baseRouteService.updateStartInfo(dto);
        return new ResponseEntity<BaseRouteStartDto>(startDto, HttpStatus.OK);
    }
}