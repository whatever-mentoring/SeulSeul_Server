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
import com.seulseul.seulseul.service.result.ComputeResultService;
import com.seulseul.seulseul.service.result.UpdateResultService;
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
    private final UpdateResultService updateResultService;
    private final ComputeResultService computeResultService;

    @GetMapping("/v1/route/detail")
    public ResponseEntity<ResponseData> routeDetail(@RequestHeader("Auth") UUID uuid) throws ParseException, IOException {
        User user = userService.getUserByUuid(uuid);
        BaseRoute baseRoute = baseRouteService.findByUser(user);
        RouteDetailDto routeDetailDto = new RouteDetailDto();
        // 첫번째 작동 시
        if (baseRoute.getSID() == 0 && baseRoute.getEID() == 0) {
            routeDetailDto = computeResultService.computeTime(baseRoute.getId());
        }
        // 출발지나 도착지 좌표가 변경되어 작동 시
        else {
            routeDetailDto = updateResultService.getUpdatedResult(baseRoute.getId());
        }
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
        ResponseData responseData = new ResponseData(200, wrapDto);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}