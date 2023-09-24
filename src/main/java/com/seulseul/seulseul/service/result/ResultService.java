package com.seulseul.seulseul.service.result;

import com.seulseul.seulseul.dto.android.RouteDetailDto;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.stopTimeList.StopTimeList;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import com.seulseul.seulseul.repository.user.UserRepository;
import com.seulseul.seulseul.service.android.RouteDetailService;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import com.seulseul.seulseul.service.stopTimeList.StopTimeListService;
import com.seulseul.seulseul.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final UserService userService;
    private final BaseRouteService baseRouteService;
    private final StopTimeListService stopTimeListService;
    private final RouteDetailService routeDetailService;
    private final UserRepository userRepository;
    private final BaseRouteRepository baseRouteRepository;

    public BaseRoute getResult(User user) throws IOException {
        // 1. SID, EID 받기
        BaseRoute baseRoute = baseRouteService.getStationIdAndName(user);

        // 2. BaseRoute에 저장할 데이터 처리하는 findTransferData 부르기
        baseRouteService.findTransferData(baseRoute.getId());

        // 3. 역마다 도착하는 시간 리스트로 가져오기
        StopTimeList stopTimeList = stopTimeListService.findStopTimeListData(baseRoute.getId());
//        StopTimeList stopTimeList = stopTimeListService.findStopTimeListData(baseRoute);
        return baseRoute;
    }

    public RouteDetailDto getRouteDetail(BaseRoute baseRoute) {
        // 4. 사용자가 역을 타는데 필요한 시간들 가져오기
        RouteDetailDto routeDetailDto = routeDetailService.routeDetailFromBaseRoute(baseRoute.getId());
        return routeDetailDto;
    }

}
