package com.seulseul.seulseul.service.alarm;

import com.seulseul.seulseul.config.CustomException;
import com.seulseul.seulseul.config.ErrorCode;
import com.seulseul.seulseul.dto.alarm.AlarmDto;
import com.seulseul.seulseul.dto.alarm.AlarmReqDto;
import com.seulseul.seulseul.entity.alarm.Alarm;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.repository.alarm.AlarmRepository;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final BaseRouteRepository baseRouteRepository;

    @Transactional(readOnly = false)
    public AlarmDto saveAlarm(AlarmReqDto dto, User user) {
        // 경로 찾기
        BaseRoute baseRoute = baseRouteRepository.findByIdAndUser(dto.getBase_route_id(), user)
                .orElseThrow(() -> new CustomException(ErrorCode.BASEROUTE_NOT_FOUND));
        // 알림 저장하기
        Alarm alarm = alarmRepository.save(new Alarm(dto.getAlarmTime(), dto.getAlarmTerm()));
        // 경로에도 저장해주기(oneToOne이므로)
        baseRoute.saveAlarmInfo(alarm);
        return new AlarmDto(alarm);
    }

}
