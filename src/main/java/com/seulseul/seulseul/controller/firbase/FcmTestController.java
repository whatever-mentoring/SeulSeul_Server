package com.seulseul.seulseul.controller.firbase;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.seulseul.seulseul.dto.Response.ResponseData;
import com.seulseul.seulseul.entity.TokenKey;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import com.seulseul.seulseul.service.firebase.NotificationService;

import com.seulseul.seulseul.service.firebase.FcmService;
import com.seulseul.seulseul.service.firebase.NotificationService;
import com.seulseul.seulseul.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FcmTestController {
    private final NotificationService notificationService;
    private final UserService userService;
    private final BaseRouteService baseRouteService;
    private final TokenKey tokenKey;
    private final FcmService fcmService;

    //    @Scheduled(fixedRate = 10000)
//    @GetMapping("/v1/fcm/test")
//    public ResponseEntity<ResponseData> fcmTest() throws FirebaseMessagingException {
//        User user = userService.getUserByUuid(uuid);
//        BaseRoute baseRoute = baseRouteService.findByUser(user);
//        if (baseRoute.getAlarm().isAlarmEnabled() == true) {
//            String response = notificationService.sendToken();
//            ResponseData responseData = new ResponseData(200, response);
//            return new ResponseEntity<>(responseData, HttpStatus.OK);
//        }
////        ResponseData responseData = new ResponseData(200, response);
////        return new ResponseEntity<>(responseData, HttpStatus.OK);
//    }
   /* @PostMapping("/v1/notice")
    public String sendPush(@RequestHeader("Auth") UUID uuid) throws FirebaseMessagingException {
        // 사용자 정보
        User user = userService.getUserByUuid(uuid);
        BaseRoute baseRoute = baseRouteService.findByUser(user);

        // 알림을 활성화했을 때만 알림 보내기
        if (baseRoute.getAlarm().isAlarmEnabled()) {
            // 현재 위치와 알람 시간/간격 가져오기
            Long original = baseRoute.getAlarm().getAlarmTime();
            Long alarmTerm = baseRoute.getAlarm().getAlarmTerm();
            String alarmTime = "";
            String pos = baseRoute.getFirstStation();

            // 이전 알람 시간 계산
//            Long lastNotificationTime = baseRoute.getLastNotificationTime();
//            Long currentTime = System.currentTimeMillis();
//            Long timeDifference = currentTime - lastNotificationTime;
            if (original >= 60) {
                if (original%60 == 0) {
                    alarmTime = original/60 +"시간 " + "00분";
                } else {
                    alarmTime = original/60 +"시간 " + original%60 + "분";
                }
            } else {
                alarmTime = original + "분";
            }

            String body = "마지막 위치 "+ pos + "역을 기준으로 "+ alarmTime +" 뒤에 막차가 끊깁니다!";
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 3); // 시간을 3시로 설정
            calendar.set(Calendar.MINUTE, 10);      // 분을 0분으로 설정
            calendar.set(Calendar.SECOND, 0);      // 초를 0초로 설정
            calendar.set(Calendar.MILLISECOND, 0); // 밀리초를 0밀리초로 설정
            calendar.add(Calendar.HOUR_OF_DAY, -1); // 1시간 전
            calendar.add(Calendar.MINUTE, -30);     // 30분 전
            int intervalMinutes = 1;
            Long currentTime = System.currentTimeMillis();
            Long minutesUntil3AM = calendar.getTimeInMillis() / (60 * 1000);
            while (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                calendar.add(Calendar.MINUTE, intervalMinutes);
            }
// 현재 시간이 설정한 시간 이후인지 확인
            if (currentTime >= minutesUntil3AM * 60 * 1000) {
                // 알림 메시지 생성
                Message message = Message.builder()
                        .putData("title", "SeulSeul")
                        .putData("body", body)
                        .setToken(tokenKey.getToken()) // 사용자의 FCM 토큰 설정
                        .build();

                // 알림 전송
                String response = FirebaseMessaging.getInstance().send(message);
                // 확인 용 프린트 문
                System.out.println(message);
                return "알림을 전송했습니다: " + response;
            } else {
                return "알림 간격이 아직 지나지 않았습니다.";
            }
        } else {
            return "알림이 비활성화되어 있습니다.";
        }
    }*/

    @PostMapping("/v1/fcm/test")
    public void send(@RequestHeader("Auth") UUID uuid) throws FirebaseMessagingException {
        User user = userService.getUserByUuid(uuid);
        BaseRoute baseRoute = baseRouteService.findByUser(user);
        fcmService.schedule(baseRoute);
    }
}