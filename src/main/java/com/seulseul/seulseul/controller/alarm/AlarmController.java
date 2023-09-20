package com.seulseul.seulseul.controller.alarm;

import com.seulseul.seulseul.dto.Response.ResponseData;
import com.seulseul.seulseul.dto.alarm.AlarmDto;
import com.seulseul.seulseul.dto.alarm.AlarmReqDto;
import com.seulseul.seulseul.dto.alarm.AlarmUpdateDto;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.service.alarm.AlarmService;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import com.seulseul.seulseul.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AlarmController {
    private final AlarmService alarmService;
    private final UserService userService;

    @PostMapping("/v1/alarm")
    public ResponseEntity<ResponseData> saveAlarm(@RequestBody AlarmReqDto dto, @RequestHeader("Auth") UUID uuid) {
        User user = userService.getUserByUuid(uuid);
        AlarmDto alarmDto = alarmService.saveAlarm(dto, user);
        ResponseData responseData = new ResponseData(200, alarmDto);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PatchMapping("/v1/alarm")
    public ResponseEntity<ResponseData> updateAlarm(@RequestBody AlarmUpdateDto updateDto, @RequestHeader("Auth") UUID uuid) {
        User user = userService.getUserByUuid(uuid);
        AlarmDto alarmDto = alarmService.updateAlarm(updateDto, user);
        ResponseData responseData = new ResponseData(200, alarmDto);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
