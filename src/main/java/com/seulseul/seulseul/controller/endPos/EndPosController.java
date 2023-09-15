package com.seulseul.seulseul.controller.endPos;

import com.seulseul.seulseul.dto.endPos.EndPosDto;
import com.seulseul.seulseul.service.endPos.EndPosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequiredArgsConstructor    //service 선언한 후 초기화 단계 필요한데, 초기화하지 않아도 되도록 해줌
public class EndPosController {
    private final EndPosService endPosService;

    @PostMapping("/addDest")
    public ResponseEntity<?> addDest(@RequestBody EndPosDto form) {
        EndPosDto dto = endPosService.addDest(form);

        return ResponseEntity.ok(dto);
    }

}
