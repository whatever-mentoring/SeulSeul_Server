package com.seulseul.seulseul.service.endPos;

import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.dto.endPos.EndPosDto;
import com.seulseul.seulseul.dto.endPos.EndPosResDto;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.endPos.EndPos;
import com.seulseul.seulseul.entity.user.User;
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
    public EndPosResDto addDest(EndPosDto form, User user) {
        //1. endPos table에 저장
//        EndPos endPos = endPosRepository.save(form.toEntity(form));
        // 생성자로 저장
        EndPos endPos = endPosRepository.save(new EndPos(form));
        EndPosDto endPosDto = endPos.toDto(endPos);

        //2. baseRoute table에 저장
        BaseRouteDto baseRouteDto = new BaseRouteDto();
        baseRouteDto.setId(endPos.getId());
        baseRouteDto.setEndX(endPosDto.getEndX());
        baseRouteDto.setEndY(endPosDto.getEndY());
        baseRouteDto.setUser(user);
//        baseRouteRepository.save(baseRouteDto.toEntity(baseRouteDto));
        // 생성자로 저장
        baseRouteRepository.save(new BaseRoute(baseRouteDto));
        return new EndPosResDto(endPos.getId(), baseRouteDto.getId(), endPos.getEndX(), endPos.getEndY(), endPos.getEndNickName()
        , endPos.getRoadNameAddress());
    }
}
