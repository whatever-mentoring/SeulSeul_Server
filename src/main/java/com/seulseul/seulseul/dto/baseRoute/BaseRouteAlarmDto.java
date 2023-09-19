package com.seulseul.seulseul.dto.baseRoute;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseRouteAlarmDto {
    private Long id;
    private boolean alarmEnabled;
    private Long alarmTime;
    private Long alarmTerm;
}
