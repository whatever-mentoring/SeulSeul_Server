package com.seulseul.seulseul.service.endPos;

import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.endPos.EndPos;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import com.seulseul.seulseul.repository.endPos.EndPosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EndPosUpdateService {
    private final EndPosRepository endPosRepository;
    private final BaseRouteRepository baseRouteRepository;

    @Transactional(readOnly = false)
    public void updateCurrentEndPos(EndPos endPos, BaseRoute baseRoute) {
        baseRoute.updateEndCoordination(endPos.getEndX(), endPos.getEndY());
    }
}
