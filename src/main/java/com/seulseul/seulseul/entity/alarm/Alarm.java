package com.seulseul.seulseul.entity.alarm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Alarm {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alarm_id")
	private Long id;

	private boolean alarmEnabled;

	private Long alarmTime;

	private int alarmTerm;

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
		} else {
			this.alarmEnabled = true;
		}
	}

	public void setAlarmEnabled() {
		this.alarmEnabled = false;
	}
}
