package com.seulseul.seulseul.service.endPos;

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
public class EndPosService {
    private final EndPosRepository endPosRepository;
    @Transactional
    public EndPosDto addDest(EndPosDto form) {
        EndPos entity = endPosRepository.save(form.toEntity(form));
        EndPosDto dto = entity.toDto(entity);
        return dto;
    }
}
