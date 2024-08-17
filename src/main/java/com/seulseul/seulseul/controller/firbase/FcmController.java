package com.seulseul.seulseul.controller.firbase;//package com.seulseul.seulseul.controller.firbase;

import java.io.IOException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.seulseul.seulseul.dto.firebase.FCMDto;
import com.seulseul.seulseul.dto.response.ResponseData;
import com.seulseul.seulseul.entity.TokenKey;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import com.seulseul.seulseul.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FcmController {
	private final BaseRouteService baseRouteService;
	private final UserService userService;
	private final TokenKey tokenKey;

	@PostMapping("/v1/fcm/check")
	public ResponseEntity<ResponseData> getTransfer(@RequestHeader("Auth") UUID uuid,
		@RequestBody FCMDto fcmDto) throws IOException {
		User user = userService.findUserByUuid(uuid);
		userService.saveToken(user, fcmDto);
		ResponseData responseData = new ResponseData(200, null);
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@RequestMapping("/send/token")
	public String creteToken(@RequestHeader("Auth") UUID uuid) throws FirebaseMessagingException {
		User user = userService.findUserByUuid(uuid);
		BaseRoute baseRoute = baseRouteService.findBaseRouteByUser(user);
		String pos = baseRoute.getFirstStation();
		Long original = baseRoute.getAlarm().getAlarmTime();
		String alarmTime = "";
		int alarmTerm = baseRoute.getAlarm().getAlarmTerm();

		if (original >= 60) {
			if (original % 60 == 0) {
				alarmTime = original / 60 + "시간 " + "00분";
			} else {
				alarmTime = original / 60 + "시간 " + original % 60 + "분";
			}
		} else {
			alarmTime = original + "분";
		}

		String body = "마지막 위치 " + pos + "역을 기준으로 " + alarmTime + " 뒤에 막차가 끊깁니다!";

		// See documentation on defining a message payload.
		Message message = Message.builder()
			.putData("title", "SeulSeul")
			.putData("body", body)
			.setToken(tokenKey.getToken())
			.build();

		// Send a message to the device corresponding to the provided
		// registration token.
		String response = FirebaseMessaging.getInstance().send(message);
		// Response is a message ID string.

		return body;
	}
}