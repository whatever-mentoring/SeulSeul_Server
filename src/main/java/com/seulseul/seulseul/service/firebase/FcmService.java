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
import java.time.format.DateTimeFormatter;
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

        // 막차 시간
        String lastTime = timeList[timeSize - 2];
        System.out.println(lastTime);
        LocalDateTime startTime;
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime lastTimeDateTime;

        // 막차 시간 String -> LocalDateTime으로 변환
        if (lastTime.startsWith("24:")) {
            lastTime = "00" + lastTime.substring(2); // 24를 00으로 변경
            lastTimeDateTime = LocalDateTime.parse(today.toString() + " " + lastTime, formatter);
        } else {
            lastTimeDateTime = LocalDateTime.parse(today.toString() + " " + lastTime, formatter);
        }

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
        // 알림 시작 시간은 막차 시간 - alarmTime
        startTime = lastTimeDateTime.minusMinutes(alarmTime);
        System.out.println(startTime);
        while (startTime.isBefore(lastTimeDateTime)) {
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
                        /* sendToken에 알림을 줄 때마다 최신의 baseRoute(latest)보내줘서 거기서 알림 on/off 검사 */
                        BaseRoute latest = baseRouteRepository.findById(baseRoute.getId())
                                .orElseThrow(() -> new CustomException(ErrorCode.BASEROUTE_NOT_FOUND));
                        notificationService.sendToken(latest);
                    } catch (FirebaseMessagingException e) {
                        e.printStackTrace();
                    }
                },
                Date.from(notifyAt.atZone(ZoneId.systemDefault()).toInstant())
        );


    }
}