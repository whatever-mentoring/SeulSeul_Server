package com.seulseul.seulseul.controller.stopTimeList;

import com.seulseul.seulseul.dto.Response.ResponseData;
import com.seulseul.seulseul.entity.stopTimeList.StopTimeList;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import com.seulseul.seulseul.service.stopTimeList.StopTimeListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Controller
@RestController
@RequiredArgsConstructor    //service 선언한 후 초기화 단계 필요한데, 초기화하지 않아도 되도록 해줌
@Slf4j
public class StopTimeListController {
    private final StopTimeListService stopTimeListService;



//    @GetMapping("/stopTimeList/{id}")
//    public ResponseEntity<ResponseData> findTransfer(@PathVariable Long id, @RequestHeader("Auth") UUID uuid) throws IOException {
//        //id를 통해 해당 baseRoute 찾기
//        Optional<BaseRoute> baseRoute = baseRouteService.getStationIdAndName(id);
//        //출발역과 환승역, 목적지 역의 정차시간 가져오기
//        //출발역
//        stopTimeListService.getStopTimeListFromAPI(baseRoute.get().getSID(),baseRoute.get().getWayCode().get(0));
//        //환승역 하나의 경우
//        stopTimeListService.getStopTimeListFromAPI(baseRoute.get().getExSID().get(0),baseRoute.get().getWayCode().get(0));
//        stopTimeListService.getStopTimeListFromAPI(baseRoute.get().getExSID().get(0),baseRoute.get().getWayCode().get(1));
//        //목적지역
//        stopTimeListService.getStopTimeListFromAPI(baseRoute.get().getEID(),baseRoute.get().getWayCode().get(1));
//
//        ResponseData responseData = new ResponseData(200, result);
//        return new ResponseEntity<>(responseData, HttpStatus.OK);
//    }

    @PostMapping("/stopTimeList/{id}")
    public ResponseEntity<ResponseData> findStopTimeList(@PathVariable Long id) throws IOException {
        StopTimeList stopTimeList = stopTimeListService.findStopTimeListData(id);
        ResponseData responseData = new ResponseData(200, stopTimeList);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
