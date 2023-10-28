package com.seulseul.seulseul.controller.baseRoute;

import com.seulseul.seulseul.dto.Response.ResponseData;
import com.seulseul.seulseul.dto.alarm.AlarmDto;
import com.seulseul.seulseul.dto.alarm.AlarmReqDto;
import com.seulseul.seulseul.dto.android.RouteDetailDto;
import com.seulseul.seulseul.dto.android.RouteDetailWrapDto;
import com.seulseul.seulseul.dto.baseRoute.*;
import com.seulseul.seulseul.entity.android.RouteDetail;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.service.android.RouteDetailService;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import com.seulseul.seulseul.service.baseRoute.BaseRouteStartService;
import com.seulseul.seulseul.service.firebase.FcmService;
import com.seulseul.seulseul.service.result.ComputeResultService;
import com.seulseul.seulseul.service.result.UpdateResultService;
import com.seulseul.seulseul.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;
import java.util.UUID;

@Controller
@RestController
@RequiredArgsConstructor    //service 선언한 후 초기화 단계 필요한데, 초기화하지 않아도 되도록 해줌
@Slf4j
public class BaseRouteController {

    private final BaseRouteService baseRouteService;
    private final UserService userService;
    private final BaseRouteStartService baseRouteStartService;

    private final ComputeResultService computeResultService;
    private final RouteDetailService routeDetailService;
    private final UpdateResultService updateResultService;
    private final FcmService fcmService;

    @PostMapping("/v1/start")
    public ResponseEntity<ResponseData> saveStartInfo(@RequestBody BaseRouteStartReqDto dto, @RequestHeader("Auth") UUID uuid) throws IOException, ParseException {
        User user = userService.getUserByUuid(uuid);
        BaseRoute baseRoute = baseRouteService.findByUser(user);
        BaseRouteStartDto reqDto = baseRouteService.saveStartInfo(dto, user);
        ResponseData responseData = new ResponseData(200, reqDto);
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }

    @PatchMapping("/v1/start")
    public ResponseEntity<ResponseData> updateStartInfo(@RequestBody BaseRouteStartUpdateDto dto, @RequestHeader("Auth") UUID uuid) throws IOException, ParseException {
        User user = userService.getUserByUuid(uuid);
        BaseRouteStartDto startDto = baseRouteStartService.updateStartInfo(dto, user);
        BaseRoute baseRoute = baseRouteService.findByUser(user);

        //<추가>baseRoute 경로 설정
        RouteDetailDto routeDetailDto = new RouteDetailDto();
        routeDetailDto = updateResultService.getUpdatedResult(baseRoute.getId());
        RouteDetailWrapDto wrapDto = new RouteDetailWrapDto();
        // RouteDetail DB에 저장
        RouteDetail routeDetail = routeDetailService.saveRouteDetail(routeDetailDto, baseRoute);
        // 환승이 있으면
        if (routeDetailDto.getExName() != null) {
            wrapDto.setBodyExList(routeDetailDto);
        }
        // 환승이 없으면
        else {
            wrapDto.setBodyList(routeDetailDto);
        }
        wrapDto.setTimeList(routeDetailDto);

        ResponseData responseData = new ResponseData(200, startDto);
        if (baseRoute.getAlarm() != null && baseRoute.getAlarm().isAlarmEnabled() == true) {
            fcmService.schedule(baseRoute);
        }

        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);
    }
}