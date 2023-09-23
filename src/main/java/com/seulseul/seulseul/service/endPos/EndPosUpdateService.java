package com.seulseul.seulseul.service.endPos;

import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.endPos.EndPos;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import com.seulseul.seulseul.repository.endPos.EndPosRepository;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EndPosUpdateService {
    private final EndPosRepository endPosRepository;
    private final BaseRouteRepository baseRouteRepository;
    private final BaseRouteService baseRouteService;

    @Transactional(readOnly = false)
    public void updateCurrentEndPos(EndPos endPos, BaseRoute baseRoute) throws IOException {
        baseRoute.updateEndCoordination(endPos.getEndX(), endPos.getEndY());
        // 오디세이 경로 업데이트 메소드 불러오기 -> getUrl에 새로운 endX, endY 좌표 넣어주기
        baseRouteService.getUrl(baseRoute.getStartX(), baseRoute.getStartY(), baseRoute.getEndX(), baseRoute.getEndY());
    }
}
