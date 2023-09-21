package com.seulseul.seulseul.service.endPos;

import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.dto.endPos.EndPosDto;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.endPos.EndPos;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import com.seulseul.seulseul.repository.endPos.EndPosRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j  //log.info() 사용가능
@Service
public class EndPosService {
    private final EndPosRepository endPosRepository;
    private final BaseRouteRepository baseRouteRepository;

    @Transactional
    public EndPosDto addDest(EndPosDto form, User user) {
        //1. endPos table에 저장(첫 번째 + 두 번째 접속)
        EndPos endPos = endPosRepository.save(new EndPos(form));
        endPos.setUser(user);
        EndPosDto endPosDto = endPos.toDto(endPos);

        //2. baseRoute table에 저장
        BaseRouteDto baseRouteDto = new BaseRouteDto();

        /*
        1. 최초 접속 시 최초 본가 설정
        최초 접속 구분 -> user로 한다.
        만약 최초 접속이면 BaseRoute에 uuid가 저장되어있지 않을 것
        BaseRoute, EndPos에 모두 저장
        */
        if (baseRouteRepository.findByUser(user).isEmpty()) {
            baseRouteDto.setId(endPos.getId());
            baseRouteDto.setEndX(endPosDto.getEndX());
            baseRouteDto.setEndY(endPosDto.getEndY());
            baseRouteDto.setUser(user);
            baseRouteRepository.save(new BaseRoute(baseRouteDto));
        }
        /*
        2. 두 번째 접속 시 본가 주소 추가
        두 번째 접속 시부터 본가 주소 추가 시에는 EndPos에만 저장해준다.
        */
        return endPosDto;
    }

    // 사용자의 모든 endPos 가져오기
    public List<EndPos> getAllEndPos(User user) {
        List<EndPos> endPosList = endPosRepository.findAllByUser(user);
        return endPosList;
    }
}