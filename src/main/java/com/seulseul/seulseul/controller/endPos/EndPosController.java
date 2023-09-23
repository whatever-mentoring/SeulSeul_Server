package com.seulseul.seulseul.controller.endPos;

import com.seulseul.seulseul.config.CustomException;
import com.seulseul.seulseul.config.ErrorCode;
import com.seulseul.seulseul.dto.Response.ResponseData;
import com.seulseul.seulseul.dto.endPos.EndPosDto;
import com.seulseul.seulseul.dto.endPos.EndPosResDto;
import com.seulseul.seulseul.entity.endPos.EndPos;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.service.endPos.EndPosService;
import com.seulseul.seulseul.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RestController
@RequiredArgsConstructor    //service 선언한 후 초기화 단계 필요한데, 초기화하지 않아도 되도록 해줌
public class EndPosController {
    private final EndPosService endPosService;
    private final UserService userService;

    //(1)endPos table에 저장: 사용자가 입력한 값에 기반 -> (2)baseRoute table에 저장
    @PostMapping("/v1/end")
    public ResponseEntity<?> addDest(@RequestBody EndPosDto form, @RequestHeader("Auth") UUID uuid) {
        User user = userService.getUserByUuid(uuid);
        EndPosResDto dto = endPosService.addDest(form, user);
        ResponseData responseData = new ResponseData(200, dto);
        return ResponseEntity.ok(responseData);
    }

    // 유저의 모든 목적지 List로 보여주기
    @GetMapping("/v1/end")
    public ResponseEntity<ResponseData> getAllEndPos(@RequestHeader("Auth") UUID uuid) {
        User user = userService.getUserByUuid(uuid);
        List<EndPos> endPosList = endPosService.getAllEndPos(user);
        ResponseData responseData = new ResponseData(200, endPosList);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    // 선택한 목적지 보여주기
    @GetMapping("/v1/end/{id}")
    public ResponseEntity<ResponseData> getEndPos(@RequestHeader("Auth") UUID uuid, @PathVariable("id") Long id) throws IOException {
        User user = userService.getUserByUuid(uuid);
        EndPosResDto dto = endPosService.getEndPos(id, user);
        ResponseData responseData = new ResponseData(200, dto);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}