package com.seulseul.seulseul.service.result;

import com.seulseul.seulseul.dto.android.RouteDetailDto;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.service.android.RouteDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComputeResultService {
    private final ResultService resultService;
    private final RouteDetailService routeDetailService;

    @Transactional
    public RouteDetailDto computeTime(Long base_route_id) throws IOException, ParseException {
        //SID + transfer + 모든 list 받기
        BaseRoute baseRoute = resultService.getResult(base_route_id);
        //
        RouteDetailDto routeDetailDto = resultService.getRouteDetail(baseRoute);
        System.out.println("dto: "+routeDetailDto);
        //실제 시간 계산 로직 => 뒤에서부터 확인
        List<String> timeList = routeDetailService.compute(baseRoute.getId());

        //재확인 => 목적지 역 이전에 먼저 끊기는 역이 존재하는 경우 해당 시간에 맞춰 timeList 변경
        timeList = Collections.singletonList(routeDetailService.checkTimeList(baseRoute.getId(), timeList));

        //시간 업데이트
        routeDetailService.updateTimeList(baseRoute.getId(), routeDetailDto, timeList.get(0));

        return routeDetailDto;
    }
}