package com.seulseul.seulseul.service.endPos;

import com.seulseul.seulseul.config.CustomException;
import com.seulseul.seulseul.config.ErrorCode;
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

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j  //log.info() 사용가능
@Service
public class EndPosService {
    private final EndPosRepository endPosRepository;
    private final BaseRouteRepository baseRouteRepository;

    @Transactional
    public EndPosResDto addDest(EndPosDto form, User user) {
        //1. endPos table에 저장(첫 번째 + 두 번째 접속)
        EndPos endPos = endPosRepository.save(new EndPos(form));
        endPos.setUser(user);
        EndPosDto endPosDto = endPos.toDto(endPos);

        //2. baseRoute table에 저장
        /*
        1. 최초 접속 시 최초 본가 설정
        최초 접속 구분 -> user로 한다.
        만약 최초 접속이면 BaseRoute에 uuid가 저장되어있지 않을 것
        BaseRoute, EndPos에 모두 저장
        */
        if (baseRouteRepository.findByUser(user).isEmpty()) {
            BaseRouteDto baseRouteDto = new BaseRouteDto();
            // baseRouteDto.setId(endPos.getId()); // 변경해야할듯
            System.out.println(endPos.getId());
            baseRouteDto.setEndX(endPosDto.getEndX());
            baseRouteDto.setEndY(endPosDto.getEndY());
            baseRouteDto.setUser(user);
            BaseRoute baseRoute = baseRouteRepository.save(new BaseRoute(baseRouteDto));
            return new EndPosResDto(endPos.getId(), baseRoute.getId(), endPos.getEndX(), endPos.getEndY(), endPos.getEndNickName()
                    , endPos.getRoadNameAddress(), endPos.getJibunAddress());
        }
        /*
        2. 두 번째 접속 시 본가 주소 추가
        두 번째 접속 시부터 본가 주소 추가 시에는 EndPos에만 저장해준다.
        */
        // EndPosResDto로 응답 -> base_route_id가 필요하다.
        Optional<BaseRoute> baseRoute = baseRouteRepository.findByUser(user);
        if (baseRoute.isEmpty()) {
            throw new CustomException(ErrorCode.BASEROUTE_NOT_FOUND);
        }
        return new EndPosResDto(endPos.getId(), baseRoute.get().getId(), endPos.getEndX(), endPos.getEndY(), endPos.getEndNickName()
                , endPos.getRoadNameAddress(), endPos.getJibunAddress());
    }

    // 사용자의 모든 endPos(목적지) 가져오기
    public List<EndPos> getAllEndPos(User user) {
        List<EndPos> endPosList = endPosRepository.findAllByUser(user);
        return endPosList;
    }

    // 선택한 endPos(목적지) 가져오기
    public EndPosResDto getEndPos(Long id, User user) {
        // endPos id와 uuid로 디비에 저장된 목적지 가져옴
        EndPos endPos = endPosRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new CustomException(ErrorCode.ENDPOS_NOT_FOUND));
        // EndPosResDto에서 base_route_id 넘겨주기 위해서 baseRoute 가져오기
        BaseRoute baseRoute = baseRouteRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(ErrorCode.BASEROUTE_NOT_FOUND));
        return new EndPosResDto(endPos, baseRoute.getId());
    }
}