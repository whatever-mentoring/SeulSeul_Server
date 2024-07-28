package com.seulseul.seulseul.service.result;

import com.seulseul.seulseul.dto.android.RouteDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class UpdateResultService {
    private final ComputeResultService computeResultService;

    // 시작, 도착 좌표 변경 시 디비 업데이트
    public RouteDetailDto getUpdatedResult(Long base_route_id) throws IOException, ParseException {
        RouteDetailDto routeDetailDto = computeResultService.computeTime(base_route_id);
        return routeDetailDto;
    }
}