package com.seulseul.seulseul.dto.baseRoute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseRouteAlarmReqDto {
    private Long id;
    private boolean isAlarm;
    private Long alarmTime;
    private Long alarmTerm;
}
