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

        RouteDetailDto detailDto = new RouteDetailDto();
        //firstStation, lastStation, exName, exWalkTime, fastTrainDoor, laneName, wayName
        detailDto.updateFromBaseRoute(baseRoute.getFirstStation(), baseRoute.getLastStation(),baseRoute.getExName(), baseRoute.getExWalkTime(), baseRoute.getFastTrainDoor(), baseRoute.getLaneName(), baseRoute.getWayName());
        return detailDto;
    }

    @Transactional(readOnly = false)
    public List<String> compute(Long id) throws ParseException {
        //id로 stopTimeList 가져오기
        List<StopTimeList> stopTimeLists = stopTimeListRepository.findByBaseRouteId(id);
        BaseRoute baseRoute = baseRouteRepository.findById(id).orElse(null);
        List<Integer> exWalkTime = baseRoute.getExWalkTime();

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
        int transfer=0; //0인 경우 출발, 목적역 / 1인 경우 첫번째 환승역에서 내린 경우 / 2인 경우 환승역에서 exWalkTime 계산 후 타는 경우
        int exWalkIdx = exWalkTime.size()-1;

        for (int i=stopTimeLists.size()-1; i>=0; i--) {
            lst = stopTimeLists.get(i);
            time = lst.getTime();
            index = time.size()-1;  //뒤에서 부터(stopTimeList에서 맨 뒤의 시간 index)

            if (i!=0 && i!=stopTimeLists.size()-1) {
                transfer += 1;
            } else {
                transfer = 0;
            }

            //도착역인경우
            if (resultTime.isEmpty()) {
                resultTime.add(time.get(index));
            } else {
                //직전 역에서의 시간
                prev = resultTime.get(resultTime.size()-1);
                // 콜론을 기준으로 문자열을 분리
                String[] parts = prev.split(":");

                // 분리된 문자열을 정수로 변환
                hours = Integer.parseInt(parts[0]);
                minutes = Integer.parseInt(parts[1]);

                //환승역에서 걸어서 이동하는 시간 제외
                if (transfer == 2) {
                    minutes -= exWalkTime.get(exWalkIdx);
                    if (minutes < 0) {
                        minutes += 60;
                        hours -= 1;
                    }
                    exWalkIdx -= 1;
                }

                //현재역과 직전역의 시간 비교
                while (true) {
                    //현재 역에서의 시간
                    current = time.get(index);
                    // 콜론을 기준으로 문자열을 분리
                    String[] p = current.split(":");
                    // 분리된 문자열을 정수로 변환
                    h = Integer.parseInt(p[0]);
                    m = Integer.parseInt(p[1]);
                    if (h<hours || (h==hours && m<=minutes)) {
                        resultTime.add(time.get(index));
                        break;
                    } else {
                        index -= 1;
                    }
                }
            }
        }
        //출발지부터 정렬
        List<String> result = new ArrayList<>();
        for (int i=resultTime.size()-1;i>=0;i--) {
            result.add(resultTime.get(i));
        }
        return result;
    }

    @Transactional
    public RouteDetailDto updateTimeList(Long id, RouteDetailDto detailDto, List<String> timeList) {
        BaseRoute baseRoute = baseRouteRepository.findById(id).orElse(null);

        //firstStation, lastStation, exName, exWalkTime, fastTrainDoor, laneName, wayName
        String startTime = timeList.get(0);
        String endTime = timeList.get(timeList.size()-1);

        String[] parts = startTime.split(":");
        String[] part = endTime.split(":");

        // 분리된 문자열을 정수로 변환
        int startH = Integer.parseInt(parts[0]);
        int startM = Integer.parseInt(parts[1]);
        int endH = Integer.parseInt(part[0]);
        int endM = Integer.parseInt(part[1]);

        //time
        int resultH = endH - startH;
        int resultM = endM - startM;

        if (resultM < 0) {
            resultM += 60;
        }

        String totalTime = resultH+"시간 "+resultM+"분";

        detailDto.updateTimeList(timeList, totalTime);

        return detailDto;
    }
}
