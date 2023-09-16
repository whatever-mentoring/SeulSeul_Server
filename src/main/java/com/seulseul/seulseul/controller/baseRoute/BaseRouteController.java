package com.seulseul.seulseul.controller.baseRoute;

import com.seulseul.seulseul.dto.Response.ResponseData;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteStartDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteStartReqDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteStartUpdateDto;
import com.seulseul.seulseul.entity.ApiKey;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Controller
@RestController
@RequiredArgsConstructor    //service 선언한 후 초기화 단계 필요한데, 초기화하지 않아도 되도록 해줌
@Slf4j
public class BaseRouteController {

    private final BaseRouteService baseRouteService;

    @GetMapping("/transfer/{id}")
    public ResponseEntity<ResponseData> findTransfer(@PathVariable Long id) throws IOException {
        Optional<BaseRoute> baseRoute = baseRouteService.getStationIdAndName(id);
        System.out.println("baseRoute:"+baseRoute);

        BaseRoute result = baseRouteService.findTransferData(baseRoute.get().getId());
        ResponseData responseData = new ResponseData(200, result);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
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
}