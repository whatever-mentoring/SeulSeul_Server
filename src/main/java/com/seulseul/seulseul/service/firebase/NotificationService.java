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
    public static String[] extractTimes(String input) {
        String cleaned = input.replace("\"[\\\"", "").replace("\\\"]\"", "").replace("\\\",\\\"", ",");
        String[] times = cleaned.split(",");
        return times;
    }

    public String sendToken(BaseRoute baseRoute) throws FirebaseMessagingException {
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
            }
        } else {
            alarmTime = original + "분";
        }
//        int hours = Integer.parseInt(alarmTime.split("시간")[0].trim());
//        int minutes = Integer.parseInt(alarmTime.split("분")[0].trim());
//
////        LocalTime term = LocalTime.of(hours, minutes);
//        String formattedTime = String.format("%d:%02d", hours, minutes);
//        LocalTime stan = LocalTime.parse(stringLastTime);
//        LocalTime term = LocalTime.parse(formattedTime);


        int alarmTerm = baseRoute.getAlarm().getAlarmTerm();
        int stringToIntAlarmTime = original.intValue();
        gap = stringToIntAlarmTime / alarmTerm;
        System.out.println(gap);
        if (cnt <= gap) {
            String body = "마지막 위치 "+ pos + "역을 기준으로 "+ (stringToIntAlarmTime-alarmTerm*cnt) +" 뒤에 막차가 끊깁니다!";

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

            return response;
        }
        return "알림을 모두 보냈습니다.";
    }

}