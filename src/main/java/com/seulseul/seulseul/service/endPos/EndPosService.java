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
    private final BaseRouteRepository baseRouteRepository;
    @Transactional
    public EndPosDto addDest(EndPosDto form) {
        //1. endPos table에 저장
        EndPos endPos = endPosRepository.save(form.toEntity(form));
        EndPosDto endPosDto = endPos.toDto(endPos);

        //2. baseRoute table에 저장
        BaseRouteDto baseRouteDto = new BaseRouteDto();
        baseRouteDto.setId(endPos.getId());
        baseRouteDto.setEndx(endPosDto.getEndx());
        baseRouteDto.setEndy(endPosDto.getEndy());
//        baseRouteRepository.save(baseRouteDto.toEntity(baseRouteDto));

        return endPosDto;
    }
}
