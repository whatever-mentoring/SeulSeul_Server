package com.seulseul.seulseul.service.baseRoute;

import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteTransferDto;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Slf4j  //log.info() 사용가능
@Service
public class BaseRouteService {

    private final BaseRouteRepository baseRouteRepository;
//    @Transactional
//    public BaseRouteDto getTransferStation(int SID, int EID, String s) {
//
////        BaseRouteTransferDto dto = new BaseRouteTransferDto();
////        dto.setStartStationId(SID);
////        dto.setEndStationId(EID);
////        dto.setExSID();
////
////        baseRouteRepository.save(new BaseRoute(dto.getId(), dto.getStartStationId(), dto.getEndStationId(), dto.getExSID()));
//
//        return dto;
//    }
}