package com.seulseul.seulseul.dto.alarm;

import com.seulseul.seulseul.entity.alarm.Alarm;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmDto {
    private Long id;
    private boolean alarmEnabled;
    private Long alarmTime;
    private Long alarmTerm;

    public AlarmDto(Alarm alarm) {
        this.id = alarm.getId();
        this.alarmEnabled = alarm.isAlarmEnabled();
        this.alarmTime = alarm.getAlarmTime();
        this.alarmTerm = alarm.getAlarmTerm();
    }
}
