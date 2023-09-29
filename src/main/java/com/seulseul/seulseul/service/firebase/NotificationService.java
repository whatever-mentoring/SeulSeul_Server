package com.seulseul.seulseul.service.firebase;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.seulseul.seulseul.entity.TokenKey;
import com.seulseul.seulseul.entity.android.RouteDetail;

import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import com.seulseul.seulseul.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final UserService userService;
    private final BaseRouteService baseRouteService;

    private int cnt = 0;
    private int gap;
    int lastStart;
    int lastEnd;

    public static String[] extractTimes(String input) {
        String cleaned = input.replace("\"[\\\"", "").replace("\\\"]\"", "").replace("\\\",\\\"", ",");
        String[] times = cleaned.split(",");
        return times;
    }

    public String sendToken(BaseRoute baseRoute) throws FirebaseMessagingException {

        if (cnt == 0) {
            lastStart = baseRoute.getSID();
            lastEnd = baseRoute.getEID();
        }

        String pos = baseRoute.getFirstStation();
        Long original = baseRoute.getAlarm().getAlarmTime();
        String alarmTime = "";
//        RouteDetail routeDetail = baseRoute.getRouteDetail();
//
//        String[] timeList = extractTimes(routeDetail.getTimeList());
//        int timeSize = timeList.length;

//        String lastTime = timeList[timeSize-1];
//        String stringLastTime = "20:00";
        // String -> LocalTime
//        LocalTime time = LocalTime.parse(stringLastTime);

        if (original >= 60) {
            if (original % 60 == 0) {
                alarmTime = original / 60 + "시간 " + "00분";
            } else {
                alarmTime = original / 60 + "시간 " + original % 60 + "분";

              //develop

    public String sendToken(BaseRoute baseRoute) throws FirebaseMessagingException {
        String pos = baseRoute.getFirstStation();
        Long original = baseRoute.getAlarm().getAlarmTime();
        String alarmTime = "";

        if (original >= 60) {
            if (original%60 == 0) {
                alarmTime = original/60 +"시간 " + "00분";
            } else {
                alarmTime = original/60 +"시간 " + original%60 + "분";

            }
        } else {
            alarmTime = original + "분";
        }
//fcmCompute
//        int hours = Integer.parseInt(alarmTime.split("시간")[0].trim());
//        int minutes = Integer.parseInt(alarmTime.split("분")[0].trim());
//
////        LocalTime term = LocalTime.of(hours, minutes);
//        String formattedTime = String.format("%d:%02d", hours, minutes);
//        LocalTime stan = LocalTime.parse(stringLastTime);
//        LocalTime term = LocalTime.parse(formattedTime);
        if (cnt > 1) {
            if (baseRoute.getSID() != lastStart || baseRoute.getEID() != lastEnd) {
                cnt = 0;
            }
        }

        lastStart = baseRoute.getSID();
        lastEnd = baseRoute.getEID();

        int alarmTerm = baseRoute.getAlarm().getAlarmTerm();
        int stringToIntAlarmTime = original.intValue();
        gap = stringToIntAlarmTime / alarmTerm;
        System.out.println(alarmTerm);
        System.out.println(gap);
        if (cnt < gap) {
            // 남은 시간
            int remainTime = stringToIntAlarmTime-alarmTerm*cnt;
            String remainTimetoStr = "";
            if (remainTime >= 60) {
                if (remainTime % 60 == 0) {
                    remainTimetoStr = remainTime / 60 + "시간 ";
                } else {
                    remainTimetoStr = remainTime / 60 + "시간 " + remainTime % 60 + "분";
                }
            } else {
                remainTimetoStr = remainTime + "분";
            }
            String body = "마지막 위치 "+ pos + "역을 기준으로 "+ remainTimetoStr +"뒤에 막차가 끊깁니다!";

            // See documentation on defining a message payload.
            Message message = Message.builder()
                    .putData("title", "SeulSeul")
                    .putData("body", body)
                    .setToken(baseRoute.getUser().getToken())
                    .build();
            String response = FirebaseMessaging.getInstance().send(message);

            cnt += 1;

            if (cnt > gap) {
                return "알림을 모두 보냈습니다.";
            }

            System.out.println("new" + cnt);
            if (baseRoute.getAlarm().isAlarmEnabled() == true) {
                System.out.println(body);
                return response;
            }
            System.out.println("nono");
        }
        return "알림을 모두 보냈습니다.";
    }

//develop

        String body = "마지막 위치 "+ pos + "역을 기준으로 "+ alarmTime +" 뒤에 막차가 끊깁니다!";

        LocalTime now = LocalTime.now();

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