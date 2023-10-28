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

    @PostMapping("/stopTimeList/{id}")
    public ResponseEntity<ResponseData> findStopTimeList(@PathVariable Long id) throws IOException {
        StopTimeList stopTimeList = stopTimeListService.findStopTimeListData(id);
        ResponseData responseData = new ResponseData(200, stopTimeList);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
