package com.seulseul.seulseul.service.endPos;

import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.endPos.EndPos;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import com.seulseul.seulseul.repository.endPos.EndPosRepository;
import com.seulseul.seulseul.service.result.ComputeResultService;
import com.seulseul.seulseul.service.result.ResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EndPosUpdateService {
    private final EndPosRepository endPosRepository;
    private final BaseRouteRepository baseRouteRepository;
    private final ResultService resultService;
    private final ComputeResultService computeResultService;

    @Transactional(readOnly = false)
    public void updateCurrentEndPos(EndPos endPos, BaseRoute baseRoute) throws IOException, ParseException {
        baseRoute.updateEndCoordination(endPos.getEndX(), endPos.getEndY());
        computeResultService.computeTime(baseRoute.getUser());
        // 디비 변경
    }
}
