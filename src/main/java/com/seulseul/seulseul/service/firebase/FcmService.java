package com.seulseul.seulseul.service.firebase;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.seulseul.seulseul.config.CustomException;
import com.seulseul.seulseul.config.ErrorCode;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import com.seulseul.seulseul.service.firebase.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import java.time.ZoneId;

import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class FcmService {
    private final TaskScheduler taskScheduler;
    private final NotificationService notificationService;


    private final BaseRouteRepository baseRouteRepository;

    public static String[] extractTimes(String input) {
        String cleaned = input.replace("\"[\\\"", "").replace("\\\"]\"", "").replace("\\\",\\\"", ",");
        String[] times = cleaned.split(",");
        return times;
    }

    public void schedule(BaseRoute baseRoute) {
        // scheduleFuture 개별적으로 관리
        List<ScheduledFuture<?>> scheduledFuture = new ArrayList<>();
        // 기존의 스케줄링 작업 취소
//        if (scheduledFuture != null) {
//            scheduledFuture.cancel(false);
//            scheduledFuture = null;
//        }

        long alarmTime = baseRoute.getAlarm().getAlarmTime();
        int alarmTerm = baseRoute.getAlarm().getAlarmTerm();

        // 시간 정보 추출
        String[] timeList = extractTimes(baseRoute.getRouteDetail().getTimeList());
        int timeSize = timeList.length;

        // 마지막 시간에서 알람시간을 뺀 시간부터 알람 시작
        String lastTime = timeList[timeSize - 2];
        LocalDateTime startTime;
        LocalDate today = LocalDate.now();
//        if ("24".equals(lastTime.split(":")[0])) {
//            String correctedLastTime = "00" + lastTime.substring(2);
//            LocalTime time = LocalTime.parse(correctedLastTime).plusHours(1).minusMinutes(alarmTime);
//            startTime = LocalDateTime.of(today.plusDays(1), time); // 내일의 날짜를 사용
//        } else {
//            LocalTime time = LocalTime.parse(lastTime).minusMinutes(alarmTime);
//            startTime = LocalDateTime.of(today, time); // 오늘의 날짜를 사용
//        }
//        System.out.println(startTime);
//        if (startTime.isBefore(LocalDateTime.now())) {
//            startTime = startTime.plusDays(1);
//        }
        // 일단 하드코딩
        LocalTime threeAM = LocalTime.of(5, 30); // 오전 3시 30분
        LocalDateTime lastBusTime = LocalDateTime.of(today, threeAM);
        System.out.println(lastBusTime);
        startTime = lastBusTime.minusMinutes(alarmTime);
        System.out.println(startTime);
        // 막차 일림을 시작해야하는 시간이 오면 while문 돌아감
/*        while (startTime.isBefore(lastBusTime)) {

            System.out.println("ok");

            // 비동기적으로 실행
            ScheduledFuture<?> futureTask =
                    scheduleNotification(startTime, baseRoute);
            scheduledFuture.add(futureTask);

            startTime=startTime.plusMinutes(alarmTerm); // 다음 알림 시각 계산

            System.out.println(startTime);
        }
        if (!baseRoute.getAlarm().isAlarmEnabled()) {
            // 이전
            for (ScheduledFuture<?> future : scheduledFuture) {
                future.cancel(false);
            }
        }*/
        while (startTime.isBefore(lastBusTime)) {
            System.out.println("ok");

            // 비동기적으로 실행
            ScheduledFuture<?> futureTask = scheduleNotification(startTime, baseRoute);
            scheduledFuture.add(futureTask);

            startTime = startTime.plusMinutes(alarmTerm); // 다음 알림 시각 계산

            System.out.println(startTime);
        }
/*
        public void cancelScheduledTasks() {
            for (ScheduledFuture<?> future : scheduledFuture) {
                future.cancel(false);
                scheduledFuture.clear();  // Clear the list after cancellation
            }
        }

        if (!baseRoute.getAlarm().isAlarmEnabled()) {
            // Cancel all previously scheduled tasks
            cancelScheduledTasks();
        }*/
    }

    private ScheduledFuture<?> scheduleNotification(LocalDateTime notifyAt, BaseRoute baseRoute) {

        return taskScheduler.schedule(
                () -> {
                    try {
                        /* 알림이 on일 때만 보내기
                         실제로 알림이 발생할 때는 이 때이기 때문에 여기서 alarmEnabled가 true인지 false인지 검사해야 함 */
                        BaseRoute latest = baseRouteRepository.findById(baseRoute.getId())
                                .orElseThrow(() -> new CustomException(ErrorCode.BASEROUTE_NOT_FOUND));
                        notificationService.sendToken(latest);
//                        if (latest.getAlarm().isAlarmEnabled()) {
//                            String result = notificationService.sendToken(latest);
//                        }
                    } catch (FirebaseMessagingException e) {
                        e.printStackTrace();
                    }
                },
                Date.from(notifyAt.atZone(ZoneId.systemDefault()).toInstant())
        );

      
      //develop
    private ScheduledFuture<?> scheduledFuture;
    private final BaseRouteRepository baseRouteRepository;

    public void schedule(BaseRoute baseRoute) {
        String cronExpression = convertToCron(baseRoute.getAlarm().getAlarmTerm()); // DB에서 alarmTerm을 가져와서 cron 표현식으로 변환

        System.out.println("cronExpression: "+cronExpression);

        CronTrigger cronTrigger = new CronTrigger(cronExpression);

        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }

        Runnable task = () -> {
            try {
                // DB에서 최신 정보 조회
                BaseRoute latestBaseRoute = baseRouteRepository.findById(baseRoute.getId())
                        .orElseThrow(() -> new CustomException(ErrorCode.BASEROUTE_NOT_FOUND));
                if (latestBaseRoute != null && latestBaseRoute.getAlarm().isAlarmEnabled()) {
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
    private String convertToCron(int minutesInterval) {
        return "0 0/" + minutesInterval + " * * * ?";

    }
}