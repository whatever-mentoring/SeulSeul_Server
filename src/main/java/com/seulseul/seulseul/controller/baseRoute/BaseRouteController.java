package com.seulseul.seulseul.controller.baseRoute;

import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequiredArgsConstructor    //service 선언한 후 초기화 단계 필요한데, 초기화하지 않아도 되도록 해줌
public class BaseRouteController {

//    private final ResponseData responseData;
//    public BaseRouteController(ResponseData responseData, BaseRouteService baseRouteService) {
//        this.responseData = responseData;
//        this.baseRouteService = baseRouteService;
//    }

    // BaseRouteService 선언
    private final BaseRouteService baseRouteService;


    @PostMapping("/destination")
    public ResponseEntity<?> saveDest(@RequestBody BaseRouteDto form) {
        BaseRouteDto dto = baseRouteService.saveDest(form);
        return ResponseEntity.ok(dto);
    }
}
