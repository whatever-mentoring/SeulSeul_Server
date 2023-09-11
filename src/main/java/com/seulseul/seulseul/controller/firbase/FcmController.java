package com.seulseul.seulseul.controller.firbase;//package com.seulseul.seulseul.controller.firbase;
//
//import com.seulseul.seulseul.dto.firebase.RequestDTO;
//import com.seulseul.seulseul.service.firebase.FirebaseCloudMessageService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//
//@RestController
//@RequiredArgsConstructor
//public class FcmController {
//
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
//}