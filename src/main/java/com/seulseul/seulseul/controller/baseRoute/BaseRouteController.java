package com.seulseul.seulseul.controller.baseRoute;

import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.dto.endPos.EndPosDto;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import com.seulseul.seulseul.service.endPos.EndPosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequiredArgsConstructor    //service 선언한 후 초기화 단계 필요한데, 초기화하지 않아도 되도록 해줌
public class BaseRouteController {

    // BaseRouteService 선언
    private final BaseRouteService baseRouteService;


    //Case1. baseRoute table에 저장. 실제 위경도 바로 넣어주는 경우
    @PostMapping("/destination")
    public ResponseEntity<?> saveDest(@RequestBody BaseRouteDto form) {
        BaseRouteDto dto = baseRouteService.saveDest(form);
        return ResponseEntity.ok(dto);
    }

    //Case2. baseRoute table에 저장. endPos 먼저 생성하고 해당 위경도 가져오는 경우
    //endPosId에 해당하는 위경도 가져오기. 현재는 endPos가 하나라 생각하고 구현하기에 항상 1.
    //[Update] endPos가 2개 이상일 경우 endPosId 대신 nickName 넣어서 여러 개 중 선택 가능하도록.
    @GetMapping("/getFromEndPos/{endPosId}")
    public ResponseEntity<?> getFromEndPos(@PathVariable Long endPosId) {
        BaseRouteDto dto = baseRouteService.getFromEndPos(endPosId);
        return ResponseEntity.ok(dto);
    }

}
