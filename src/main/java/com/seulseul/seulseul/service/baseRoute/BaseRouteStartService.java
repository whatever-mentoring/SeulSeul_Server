package com.seulseul.seulseul.service.baseRoute;

import com.seulseul.seulseul.config.CustomException;
import com.seulseul.seulseul.config.ErrorCode;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteStartDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteStartUpdateDto;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import com.seulseul.seulseul.service.result.UpdateResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class BaseRouteStartService {

    private final BaseRouteRepository baseRouteRepository;
    private final UpdateResultService updateResultService;

    // 현재 위치 변경하기
    @Transactional
    public BaseRouteStartDto updateStartInfo(BaseRouteStartUpdateDto dto, User user) throws IOException, ParseException {
        BaseRoute baseRoute = baseRouteRepository.findByIdAndUser(dto.getId(), user)
                .orElseThrow(() -> new CustomException(ErrorCode.BASEROUTE_NOT_FOUND));
        baseRoute.updateStartCoordination(dto.getStartX(), dto.getStartY());
//        computeResultService.computeTime(user);
        // 오디세이 api 불러와서 디비 업데이트
        updateResultService.getUpdatedResult(baseRoute.getId());
        return new BaseRouteStartDto(dto.getId(), dto.getStartX(), dto.getStartY(), baseRoute.getDayInfo());
    }
}