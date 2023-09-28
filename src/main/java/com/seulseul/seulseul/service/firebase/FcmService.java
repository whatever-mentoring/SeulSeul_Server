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

import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class FcmService {
    private final TaskScheduler taskScheduler;
    private final NotificationService notificationService;
    private ScheduledFuture<?> scheduledFuture;
    private final BaseRouteRepository baseRouteRepository;

    // 상태 변수 추가
    private boolean stopScheduling = false;

    public void schedule(BaseRoute baseRoute) {
        String cronExpression = convertToCron(baseRoute.getAlarm().getAlarmTerm()); // DB에서 alarmTerm을 가져와서 cron 표현식으로 변환

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
                    System.out.println("notice");
                    String result = notificationService.sendToken(latestBaseRoute); // FCM 메시지 전송
                    if ("알림을 모두 보냈습니다.".equals(result)) {
                        stopScheduling = true;  // 상태 변수 업데이트
                    }
                }
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
            }

            // 작업 후 상태 확인하여 스케줄링 중지
            if(stopScheduling){
                scheduledFuture.cancel(false);
                System.out.println("Scheduled Task is stopped.");
                stopScheduling=false;  //상태 초기화
            }
        };

        scheduledFuture=this.taskScheduler.schedule(task,(Trigger)cronTrigger);
    }

    // alarmTerm을 cron에 넣어주기
    private String convertToCron(int minutesInterval){
//       return "* /" + minutesInterval + " * * * *";
        return "0 0/" + minutesInterval + " * * * ?";
    }
}
