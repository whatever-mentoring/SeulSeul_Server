package com.seulseul.seulseul.controller.baseRoute;

import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteStartReqDto;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseRouteController {
    private final BaseRouteService baseRouteService;

    public BaseRouteController(BaseRouteService baseRouteService) {
        this.baseRouteService = baseRouteService;
    }

    @PostMapping("/v1/start")
    public ResponseEntity<BaseRouteDto> getStartCoordination(@RequestBody BaseRouteStartReqDto dto) {
        baseRouteService.getStartCoordination(dto);
        return new ResponseEntity<>(new BaseRouteDto(dto), HttpStatus.OK);
    }
}
