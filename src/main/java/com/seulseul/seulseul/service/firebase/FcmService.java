package com.seulseul.seulseul.service.firebase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.seulseul.seulseul.config.CustomException;
import com.seulseul.seulseul.config.ErrorCode;
import com.seulseul.seulseul.entity.android.RouteDetail;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.concurrent.ScheduledFuture;

import static com.seulseul.seulseul.dto.android.RouteDetailWrapDto.extractTimes;

@Service
@RequiredArgsConstructor
public class FcmService {
    private final TaskScheduler taskScheduler;
    private final NotificationService notificationService;
    private ScheduledFuture<?> scheduledFuture;
    private final BaseRouteRepository baseRouteRepository;


    public void schedule(BaseRoute baseRoute) throws JsonProcessingException {

        String[] timeList = extractTimes(baseRoute.getRouteDetail().getTimeList());
        System.out.println("timeList: "+timeList[0]);
//        String[] timeList = objectMapper.readValue(baseRoute.getRouteDetail().getTimeList(), String[].class);

        String cronExpression = convertToCron(timeList[0], Math.toIntExact(baseRoute.getAlarm().getAlarmTime()), baseRoute.getAlarm().getAlarmTerm()); // DB에서 alarmTerm을 가져와서 cron 표현식으로 변환

        System.out.println("cronExpression: "+cronExpression);

        CronTrigger cronTrigger = new CronTrigger(cronExpression);

        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }

        //현재시간
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();

        //출발역에서 출발 시간
        String[] parts = timeList[0].split(":");
        int h=0,m=0 ;
        if (parts.length == 2) {
            // 시간과 분을 정수로 변환합니다.
            h = Integer.parseInt(parts[0]);
            m = Integer.parseInt(parts[1]);

        } else {
            System.out.println("올바른 시간 형식이 아닙니다.");
        }
        System.out.println(hour);
        System.out.println(minute);
        System.out.println(h);
        System.out.println(m);


        if(hour == h && minute == m) {
            System.out.println("done");
            baseRoute.getAlarm().setAlarmEnabled();
        }

        Runnable task = () -> {
            try {
                // DB에서 최신 정보 조회
                BaseRoute latestBaseRoute = baseRouteRepository.findById(baseRoute.getId())
                        .orElseThrow(() -> new CustomException(ErrorCode.BASEROUTE_NOT_FOUND));
                if (latestBaseRoute != null && latestBaseRoute.getAlarm().isAlarmEnabled()) {
                    //cron에 의해 알림을 전송하는
                    System.out.println("timestamp"+now);
                    System.out.println("sendToken");
                    notificationService.sendToken(latestBaseRoute); // FCM 메시지 전송
                }
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }
        };

        scheduledFuture = this.taskScheduler.schedule(task, (Trigger) cronTrigger);

    }

    // alarmTerm을 cron에 넣어주기
    private String convertToCron(String time, int alarmTime, int minutesInterval) {

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

        int h = alarmTime/60;
        int m = alarmTime%60;


        int resultH = hour - h;
        int resultM = minute - m;

        if (resultM < 0) {
            resultH -= 1;
            resultM += 60;
        }

        LocalTime now = LocalTime.now();

        return "0 "+ resultM + "/" + minutesInterval + " " + resultH + " * * ?";
    }
}