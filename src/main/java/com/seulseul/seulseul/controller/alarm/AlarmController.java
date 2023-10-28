package com.seulseul.seulseul.controller.alarm;

import com.seulseul.seulseul.dto.Response.ResponseData;
import com.seulseul.seulseul.dto.alarm.AlarmDto;
import com.seulseul.seulseul.dto.alarm.AlarmReqDto;
import com.seulseul.seulseul.dto.alarm.AlarmUpdateDto;
import com.seulseul.seulseul.dto.android.RouteDetailDto;
import com.seulseul.seulseul.dto.android.RouteDetailWrapDto;
import com.seulseul.seulseul.entity.android.RouteDetail;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.service.alarm.AlarmService;
import com.seulseul.seulseul.service.android.RouteDetailService;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import com.seulseul.seulseul.service.firebase.FcmService;
import com.seulseul.seulseul.service.result.ComputeResultService;
import com.seulseul.seulseul.service.result.UpdateResultService;
import com.seulseul.seulseul.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.RouteMatcher;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AlarmController {
    private final AlarmService alarmService;
    private final UserService userService;
    private final BaseRouteService baseRouteService;
    private final RouteDetailService routeDetailService;
    private final ComputeResultService computeResultService;
    private final UpdateResultService updateResultService;
    private final FcmService fcmService;

    @PostMapping("/v1/alarm")
    public ResponseEntity<ResponseData> saveAlarm(@RequestBody AlarmReqDto dto, @RequestHeader("Auth") UUID uuid) throws IOException, ParseException {
        User user = userService.getUserByUuid(uuid);
        BaseRoute baseRoute = baseRouteService.findByUser(user);

        //알람 저장
        AlarmDto alarmDto = alarmService.saveAlarm(dto, user);

        //<추가>baseRoute 경로 설정
        RouteDetailWrapDto wrapDto = new RouteDetailWrapDto();
        RouteDetailDto routeDetailDto = computeResultService.computeTime(baseRoute.getId());

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

        fcmService.schedule(baseRoute);

        ResponseData responseData = new ResponseData(200, alarmDto);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PatchMapping("/v1/alarm")
    public ResponseEntity<ResponseData> updateAlarm(@RequestBody AlarmUpdateDto updateDto, @RequestHeader("Auth") UUID uuid) throws IOException, ParseException {
        User user = userService.getUserByUuid(uuid);
        BaseRoute baseRoute = baseRouteService.findByUser(user);

        //알람 수정
        AlarmDto alarmDto = alarmService.updateAlarm(updateDto, user);

        //<추가>baseRoute 경로 설정
        RouteDetailDto routeDetailDto = updateResultService.getUpdatedResult(baseRoute.getId());
        RouteDetailWrapDto wrapDto = new RouteDetailWrapDto();
        // RouteDetail DB에 저장
        RouteDetail routeDetail = routeDetailService.saveRouteDetail(routeDetailDto, baseRoute);
        if (routeDetailDto.getExName() != null) {   // 환승이 있으면
            wrapDto.setBodyExList(routeDetailDto);
        } else {                                    // 환승이 없으면
            wrapDto.setBodyList(routeDetailDto);
        }
        wrapDto.setTimeList(routeDetailDto);

        if (baseRoute.getAlarm().isAlarmEnabled() == true) {
            fcmService.schedule(baseRoute);
        }
        ResponseData responseData = new ResponseData(200, alarmDto);

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PatchMapping("/v1/alarm/enabled/{id}")
    public ResponseEntity<ResponseData> updateAlarm(@RequestHeader("Auth") UUID uuid, @PathVariable("id") Long id) {
        User user = userService.getUserByUuid(uuid);
        AlarmDto alarmDto = alarmService.updateAlarmEnabled(id, user);
        ResponseData responseData = new ResponseData(200, alarmDto);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
