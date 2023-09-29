package com.seulseul.seulseul.service.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.seulseul.seulseul.entity.TokenKey;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import com.seulseul.seulseul.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

import static com.seulseul.seulseul.dto.android.RouteDetailWrapDto.extractTimes;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final UserService userService;
    private final BaseRouteService baseRouteService;

    public String sendToken(BaseRoute baseRoute) throws FirebaseMessagingException {
        String pos = baseRoute.getFirstStation();
        Long original = baseRoute.getAlarm().getAlarmTime();
        String alarmTime = "";

        //출발시간
        String[] timeList = extractTimes(baseRoute.getRouteDetail().getTimeList());
        String time = timeList[0];
        int hour=0;
        int minute=0;

        String[] parts = time.split(":");

        if (parts.length == 2) {
            // 시간과 분을 정수로 변환합니다.
            hour = Integer.parseInt(parts[0]);
            minute = Integer.parseInt(parts[1]);

        } else {
            System.out.println("올바른 시간 형식이 아닙니다.");
        }

        LocalTime now = LocalTime.now();
        int h = now.getHour();
        int m = now.getMinute();

        int resultH = hour - h;
        int resultM = minute - m;

        if (resultM < 0) {
            resultH -= 1;
            resultM += 60;
        }


//        if (original >= 60) {
//            if (original%60 == 0) {
//                alarmTime = original/60 +"시간 " + "00분";
//            } else {
//                alarmTime = original/60 +"시간 " + original%60 + "분";
//            }
//        } else {
//            alarmTime = original + "분";
//        }

        String body = "마지막 위치 "+ pos + "역을 기준으로 "+ resultH+ "시간 "+ resultM +"분" +" 뒤에 막차가 끊깁니다!";

        System.out.println("body: "+body);
        System.out.println("timestamp: "+now);

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .putData("title", "SeulSeul")
                .putData("body", body)
                .setToken(baseRoute.getUser().getToken())
                .build();

        // Send a message to the device corresponding to the provided
        // registration token.
        String response = FirebaseMessaging.getInstance().send(message);

        return response;
    }
}