package com.seulseul.seulseul.service.baseRoute;

import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.dto.endPos.EndPosDto;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.endPos.EndPos;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import com.seulseul.seulseul.repository.endPos.EndPosRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Slf4j  //log.info() 사용가능
@Service
public class BaseRouteService {

    private final BaseRouteRepository baseRouteRepository;
    private final EndPosRepository endPosRepository;

    @Transactional
    public BaseRouteDto saveDest(BaseRouteDto form) {
        BaseRoute entity = baseRouteRepository.save(form.toEntity(form));
        BaseRouteDto dto = entity.toDto(entity);
        return dto;
    }

    @Transactional
    public BaseRouteDto getFromEndPos(Long id) {
        //1. endPos 값 가져오기
        EndPos endPos = endPosRepository.findById(id).orElse(null);
        EndPosDto endPosDto = endPos.toDto(endPos);

        //2. 원하는 위경도 값만 가져와 저장
        BaseRouteDto dto = new BaseRouteDto();
        dto.setId(endPos.getId());
        dto.setEndx(endPosDto.getEndx());
        dto.setEndy(endPosDto.getEndy());
        baseRouteRepository.save(dto.toEntity(dto));

        return dto;
    }
}