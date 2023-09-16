package com.seulseul.seulseul.controller.endPos;

import com.seulseul.seulseul.dto.Response.ResponseData;
import com.seulseul.seulseul.dto.endPos.EndPosDto;
import com.seulseul.seulseul.service.endPos.EndPosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequiredArgsConstructor    //service 선언한 후 초기화 단계 필요한데, 초기화하지 않아도 되도록 해줌
public class EndPosController {
    private final EndPosService endPosService;

    //(1)endPos table에 저장: 사용자가 입력한 값에 기반 -> (2)baseRoute table에 저장
    @PostMapping("/v1/end")
    public ResponseEntity<?> addDest(@RequestBody EndPosDto form) {
        EndPosDto dto = endPosService.addDest(form);
        ResponseData responseData = new ResponseData(200, dto);
        return new ResponseEntity<ResponseData>(responseData, HttpStatus.OK);

    }

}
