package com.seulseul.seulseul.service.android;

import com.seulseul.seulseul.dto.android.RouteDetailDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.stopTimeList.StopTimeList;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import com.seulseul.seulseul.repository.stopTimeList.StopTimeListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Slf4j  //log.info() 사용가능
@Service
@Transactional(readOnly = true)
public class RouteDetailService {
    private final BaseRouteRepository baseRouteRepository;
    private final StopTimeListRepository stopTimeListRepository;

    @Transactional(readOnly = false)
    public RouteDetailDto routeDetailFromBaseRoute(Long id) {
        BaseRoute baseRoute = baseRouteRepository.findById(id).orElse(null);
        System.out.println("baseRoute: "+baseRoute);

        RouteDetailDto detailDto = new RouteDetailDto();
        //firstStation, lastStation, exName, exWalkTime, fastTrainDoor, laneName, wayName
        detailDto.updateFromBaseRoute(baseRoute.getFirstStation(), baseRoute.getLastStation(),baseRoute.getExName(), baseRoute.getExWalkTime(), baseRoute.getFastTrainDoor(), baseRoute.getLaneName(), baseRoute.getWayName());
        return detailDto;
    }

    @Transactional(readOnly = false)
    public void compute(RouteDetailDto routeDetailDto, Long id) throws ParseException {
        //id로 stopTimeList 가져오기
        List<StopTimeList> stopTimeLists = stopTimeListRepository.findByBaseRouteId(id);

        //맨 뒤(도착역)부터
        StopTimeList lst;
        List<String> time;
        List<String> resultTime = new ArrayList<>();
        String prev;
        String current;
        int index;
        int hours;
        int minutes;
        int h;
        int m;

        for (int i=stopTimeLists.size()-1; i>=0; i--) {
            lst = stopTimeLists.get(i);
            time = lst.getTime();
            index = time.size()-1;  //뒤에서 부터(stopTimeList에서 맨 뒤의 시간 index)
            //도착역인경우
            if (resultTime.isEmpty()) {
                resultTime.add(time.get(index));
            } else {
                //앞의 시간
                prev = resultTime.get(resultTime.size()-1);
                // 콜론을 기준으로 문자열을 분리
                String[] parts = prev.split(":");

                // 분리된 문자열을 정수로 변환
                hours = Integer.parseInt(parts[0]);
                minutes = Integer.parseInt(parts[1]);

                //현재 역에서의 시간
                current = time.get(index);
                // 콜론을 기준으로 문자열을 분리
                String[] part = current.split(":");
                // 분리된 문자열을 정수로 변환
                h = Integer.parseInt(part[0]);
                m = Integer.parseInt(part[1]);

                if (h<=hours && m<=minutes) {
                    resultTime.add(time.get(index));
                } else {
                    while (true) {
                        index -= 1;
                        //현재 역에서의 시간
                        current = time.get(index);
                        // 콜론을 기준으로 문자열을 분리
                        String[] p = current.split(":");
                        // 분리된 문자열을 정수로 변환
                        h = Integer.parseInt(p[0]);
                        m = Integer.parseInt(p[1]);
                        if (h<=hours && m<=minutes) {
                            resultTime.add(time.get(index));
                            break;
                        }
                    }
                }

            }
        }
        System.out.println(resultTime);
        System.out.println(resultTime.get(0));
    }
}
