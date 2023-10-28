package com.seulseul.seulseul.controller.firbase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.seulseul.seulseul.dto.Response.ResponseData;
import com.seulseul.seulseul.entity.TokenKey;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
//import com.seulseul.seulseul.service.firebase.NotificationService;
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

    @PostMapping("/v1/notice")
    public void send(@RequestHeader("Auth") UUID uuid) throws FirebaseMessagingException, JsonProcessingException {
        User user = userService.getUserByUuid(uuid);
        BaseRoute baseRoute = baseRouteService.findByUser(user);
        fcmService.schedule(baseRoute);
    }
}