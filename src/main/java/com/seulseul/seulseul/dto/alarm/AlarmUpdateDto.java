package com.seulseul.seulseul.dto.alarm;

import lombok.Getter;

@Getter
public class AlarmUpdateDto {
    private Long id;
    private Long base_route_id;
    private Long alarmTime;
    private Long alarmTerm;
}
