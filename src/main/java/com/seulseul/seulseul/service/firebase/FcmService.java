package com.seulseul.seulseul.service.firebase;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.seulseul.seulseul.config.CustomException;
import com.seulseul.seulseul.config.ErrorCode;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class FcmService {
    private final TaskScheduler taskScheduler;
    private final NotificationService notificationService;
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