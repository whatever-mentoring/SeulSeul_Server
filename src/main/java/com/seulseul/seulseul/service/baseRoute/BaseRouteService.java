package com.seulseul.seulseul.service.baseRoute;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seulseul.seulseul.dto.baseRoute.*;
import com.seulseul.seulseul.entity.ApiKey;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BaseRouteService {
    private ApiKey apiKey;
    private BaseRouteRepository baseRouteRepository;

    public BaseRouteService(ApiKey apiKey, BaseRouteRepository baseRouteRepository) {
        this.apiKey = apiKey;
        this.baseRouteRepository = baseRouteRepository;
    }

    // 현재 위치(좌표), 요일 받아오기
    @Transactional(readOnly = false)
    public BaseRouteStartDto saveStartInfo(BaseRouteStartReqDto reqDto) {
        Optional<BaseRoute> baseRoute = baseRouteRepository.findById(reqDto.getId());
        baseRoute.get().saveStartInfo(reqDto.getStartX(), reqDto.getStartY(), reqDto.getDayInfo());
        return new BaseRouteStartDto(baseRoute.get().getId(), reqDto.getStartX(), reqDto.getStartY(), reqDto.getDayInfo());
    }

    // 현재 위치 변경하기
    @Transactional(readOnly = false)
    public BaseRouteStartDto updateStartInfo(BaseRouteStartUpdateDto dto) {
        Optional<BaseRoute> baseRoute = baseRouteRepository.findById(dto.getId());
        baseRoute.get().updateStartCoordination(dto.getStartX(), dto.getStartY());
        return new BaseRouteStartDto(dto.getId(), dto.getStartX(), dto.getStartY(), baseRoute.get().getDayInfo());
    }
}