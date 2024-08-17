package com.seulseul.seulseul.service.result;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.seulseul.seulseul.dto.android.RouteDetailDto;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.stopTimeList.StopTimeList;
import com.seulseul.seulseul.service.android.RouteDetailService;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import com.seulseul.seulseul.service.stopTimeList.StopTimeListService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResultService {
	private final BaseRouteService baseRouteService;
	private final StopTimeListService stopTimeListService;
	private final RouteDetailService routeDetailService;

	public BaseRoute findResult(Long base_route_id) throws IOException {
		// 1. SID, EID 받기
		BaseRoute baseRoute = baseRouteService.findStationIdAndName(base_route_id);

		// 2. BaseRoute에 저장할 데이터 처리하는 findTransferData 부르기
		baseRouteService.findTransferData(baseRoute.getId());

		// 3. 역마다 도착하는 시간 리스트로 가져오기
		StopTimeList stopTimeList = stopTimeListService.findStopTimeListData(baseRoute.getId());
		return baseRoute;
	}

	public RouteDetailDto findRouteDetail(BaseRoute baseRoute) throws JsonProcessingException {
		// 4. 사용자가 역을 타는데 필요한 시간들 가져오기
		RouteDetailDto routeDetailDto = routeDetailService.routeDetailFromBaseRoute(baseRoute.getId());
		return routeDetailDto;
	}
}
