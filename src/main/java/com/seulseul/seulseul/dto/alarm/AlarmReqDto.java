package com.seulseul.seulseul.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmReqDto {
    private Long base_route_id;
    private Long alarmTime;
    private int alarmTerm;
}
