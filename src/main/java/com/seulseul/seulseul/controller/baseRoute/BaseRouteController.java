package com.seulseul.seulseul.controller.baseRoute;

import com.seulseul.seulseul.dto.Response.ResponseData;
import com.seulseul.seulseul.dto.alarm.AlarmDto;
import com.seulseul.seulseul.dto.alarm.AlarmReqDto;
import com.seulseul.seulseul.dto.baseRoute.*;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import com.seulseul.seulseul.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Controller
@RestController
@RequiredArgsConstructor    //service 선언한 후 초기화 단계 필요한데, 초기화하지 않아도 되도록 해줌
@Slf4j
public class BaseRouteController {

    private final BaseRouteService baseRouteService;
    private final UserService userService;

    @GetMapping("/transfer/{id}")
    public ResponseEntity<ResponseData> findTransfer(@PathVariable Long id) throws IOException {
        Optional<BaseRoute> baseRoute = baseRouteService.getStationIdAndName(id);
        BaseRoute result = baseRouteService.findTransferData(baseRoute.get().getId());
        ResponseData responseData = new ResponseData(200, result);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/v1/start")
    public ResponseEntity<ResponseData> saveStartInfo(@RequestBody BaseRouteStartReqDto dto, @RequestHeader("Auth") UUID uuid) throws IOException {
        User user = userService.getUserByUuid(uuid);
        BaseRouteStartDto reqDto = baseRouteService.saveStartInfo(dto, user);
        ResponseData responseData = new ResponseData(200, reqDto);
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }

    @PatchMapping("/v1/start")
    public ResponseEntity<ResponseData> updateStartInfo(@RequestBody BaseRouteStartUpdateDto dto, @RequestHeader("Auth") UUID uuid) {
        User user = userService.getUserByUuid(uuid);
        BaseRouteStartDto startDto = baseRouteService.updateStartInfo(dto, user);
        ResponseData responseData = new ResponseData(200, startDto);
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }
}