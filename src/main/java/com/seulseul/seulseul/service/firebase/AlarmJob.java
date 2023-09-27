//package com.seulseul.seulseul.service.firebase;
//
//import com.google.firebase.messaging.FirebaseMessagingException;
//import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
//import com.seulseul.seulseul.entity.user.User;
//import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
//import lombok.RequiredArgsConstructor;
//import org.quartz.Job;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.springframework.stereotype.Service;
//
//@RequiredArgsConstructor
//public class AlarmJob implements Job {
//    private final NotificationService notificationService;
//    private final BaseRouteService baseRouteService;
//
//    @Override
//    public void execute(JobExecutionContext context) throws JobExecutionException {
//        User user = (User) context.getJobDetail().getJobDataMap().get("user");
//        BaseRoute baseRoute = baseRouteService.findByUser(user);
//
//        if(baseRoute.getAlarm().isAlarmEnabled() == true) {
//            try {
//                notificationService.sendToken(user.getUuid());
//            } catch (FirebaseMessagingException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
