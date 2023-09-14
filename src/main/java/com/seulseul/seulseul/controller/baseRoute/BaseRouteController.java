package com.seulseul.seulseul.controller.baseRoute;

import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteStartReqDto;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class BaseRouteController {
    private final BaseRouteService baseRouteService;

    public BaseRouteController(BaseRouteService baseRouteService) {
        this.baseRouteService = baseRouteService;
    }

    @PostMapping("/v1/start")
    public ResponseEntity<BaseRouteDto> getStartCoordination(@RequestBody BaseRouteStartReqDto dto) throws IOException {
        // 좌표값 dto에 저장해서
        BaseRouteStartReqDto reqDto = baseRouteService.getStartCoordination(dto);
        // 넘겨주기
        BaseRouteDto routeDto = baseRouteService.getStationID(reqDto.getStartX(), reqDto.getStartY(), reqDto.getDayInfo());
        return new ResponseEntity<>(routeDto, HttpStatus.OK);
    }
}