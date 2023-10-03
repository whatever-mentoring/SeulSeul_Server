package com.seulseul.seulseul.entity.alarm;

import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    private boolean alarmEnabled;

    private Long alarmTime;

    private int alarmTerm;

//    @OneToOne(mappedBy = "alarm")
//    private BaseRoute baseRoute;

    public Alarm(Long alarmTime, int alarmTerm) {
        this.alarmEnabled = true;
        this.alarmTime = alarmTime;
        this.alarmTerm = alarmTerm;
    }

    public void updateAlarm(Long alarmTime, int alarmTerm) {
        this.alarmTime = alarmTime;
        this.alarmTerm = alarmTerm;
    }

    public void updateAlarmEnabled(boolean alarmEnabled) {
        if (alarmEnabled == true) {
            this.alarmEnabled = false;
        }
        else {
            this.alarmEnabled = true;
        }
    }

    public void setAlarmEnabled() {
        this.alarmEnabled = false;
    }
}
