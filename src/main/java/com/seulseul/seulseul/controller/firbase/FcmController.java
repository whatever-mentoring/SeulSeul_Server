package com.seulseul.seulseul.controller.firbase;//package com.seulseul.seulseul.controller.firbase;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FcmController {

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
//
//
//    private void sendToToken(Push push) throws FirebaseMessagingException {
//        Message message = Message.builder()
//                .setNotification(Notification.builder()
//                        .setTitle("[광고] 테스트 광고")
//                        .setBody("테스트 내용")
//                        .build())
//                // Device를 특정할 수 있는 토큰.
//                .setToken(push.getRegistrationToken())
//                .build();
//
//        String response = FirebaseMessaging.getInstance().send(message);
//        log.debug("Successfully sent message: " + response);
//    }

}