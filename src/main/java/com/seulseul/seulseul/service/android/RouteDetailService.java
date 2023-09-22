package com.seulseul.seulseul.service.android;

import com.seulseul.seulseul.dto.android.RouteDetailDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j  //log.info() 사용가능
@Service
@Transactional(readOnly = true)
public class RouteDetailService {
    private final BaseRouteRepository baseRouteRepository;

    @Transactional(readOnly = false)
    public RouteDetailDto routeDetailFromBaseRoute(Long id) {
        BaseRoute baseRoute = baseRouteRepository.findById(id).orElse(null);
        System.out.println("baseRoute: "+baseRoute);

        RouteDetailDto detailDto = new RouteDetailDto();
        //firstStation, lastStation, exName, exWalkTime, fastTrainDoor, laneName, wayName
        detailDto.updateFromBaseRoute(baseRoute.getFirstStation(), baseRoute.getLastStation(),baseRoute.getExName(), baseRoute.getExWalkTime(), baseRoute.getFastTrainDoor(), baseRoute.getLaneName(), baseRoute.getWayName());
        return detailDto;
    }
}
