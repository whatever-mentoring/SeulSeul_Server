package com.seulseul.seulseul.repository.alarm;

import com.seulseul.seulseul.entity.alarm.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
