package com.seulseul.seulseul.controller.android;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seulseul.seulseul.dto.Response.ResponseData;
import com.seulseul.seulseul.dto.android.RouteDetailDto;
import com.seulseul.seulseul.dto.android.RouteDetailWrapDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.entity.android.RouteDetail;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.repository.user.UserRepository;
import com.seulseul.seulseul.service.alarm.AlarmService;
import com.seulseul.seulseul.service.android.RouteDetailService;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import com.seulseul.seulseul.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RouteDetailController {
    private final RouteDetailService routeDetailService;
    private final BaseRouteService baseRouteService;
    private final UserService userService;

    @GetMapping("/v1/route/detail")
    public ResponseEntity<ResponseData> routeDetail(@RequestHeader("Auth") UUID uuid) throws ParseException, IOException {
        User user = userService.getUserByUuid(uuid);
        BaseRoute baseRoute = baseRouteService.findByUser(user);

        //baseRoute에 존재하는 데이터 전달
        RouteDetailDto routeDetailDto = routeDetailService.routeDetailFromBaseRoute(baseRoute.getId());

        //실제 시간 계산 로직 => 뒤에서부터 확인
        List<String> timeList = routeDetailService.compute(baseRoute.getId());

        //재확인 => 목적지 역 이전에 먼저 끊기는 역이 존재하는 경우 해당 시간에 맞춰 timeList 변경
        String timeList2 = routeDetailService.checkTimeList(baseRoute.getId(), timeList);
        System.out.println("/v1/route/detail timeList2: "+timeList2);   //여기까지 성공

        //시간 업데이트
        routeDetailService.updateTimeList(baseRoute.getId(), routeDetailDto, timeList2);
        RouteDetailWrapDto wrapDto = new RouteDetailWrapDto();
        // RouteDetail DB에 저장
        routeDetailService.saveRouteDetail(routeDetailDto);
        wrapDto.setExWalkTimeList(routeDetailDto);
        wrapDto.setBodyList(routeDetailDto);
        wrapDto.setTimeList(routeDetailDto);
        ResponseData responseData = new ResponseData(200, wrapDto);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}