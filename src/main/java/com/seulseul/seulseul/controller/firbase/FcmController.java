package com.seulseul.seulseul.controller.firbase;//package com.seulseul.seulseul.controller.firbase;

import com.seulseul.seulseul.dto.Response.ResponseData;
//import com.seulseul.seulseul.dto.firebase.RequestDTO;
import com.seulseul.seulseul.dto.firebase.FCMDto;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
//import com.seulseul.seulseul.service.firebase.FirebaseCloudMessageService;
import com.seulseul.seulseul.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FcmController {
    private final BaseRouteService baseRouteService;
    private final UserService userService;

    @PostMapping("/v1/fcm/check")
    public ResponseEntity<ResponseData> findTransfer(@RequestHeader("Auth") UUID uuid, @RequestBody FCMDto fcmDto) throws IOException {
        User user = userService.getUserByUuid(uuid);
        BaseRoute baseRoute = baseRouteService.findByUser(user);
        userService.saveToken(uuid, fcmDto);

        ResponseData responseData = new ResponseData(200, null);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

//    private final FirebaseCloudMessageService firebaseCloudMessageService;
//
//    @PostMapping("/api/fcm")
//    public ResponseEntity pushMessage(@RequestBody RequestDTO requestDTO) throws IOException {
//        System.out.println(requestDTO.getTargetToken() + " "
//                + requestDTO.getTitle() + " " + requestDTO.getBody());
//
//        firebaseCloudMessageService.sendMessageTo(
//                requestDTO.getTargetToken(),
//                requestDTO.getTitle(),
//                requestDTO.getBody());
//        return ResponseEntity.ok().build();
//    }

}