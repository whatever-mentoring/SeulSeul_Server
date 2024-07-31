package com.seulseul.seulseul.service.endPos;

import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.endPos.EndPos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;

@Service
@Transactional(readOnly = true)
public class EndPosUpdateService {
    @Transactional
    public void updateCurrentEndPos(EndPos endPos, BaseRoute baseRoute) throws IOException, ParseException {
        baseRoute.updateEndCoordination(endPos.getEndX(), endPos.getEndY());
    }
}
