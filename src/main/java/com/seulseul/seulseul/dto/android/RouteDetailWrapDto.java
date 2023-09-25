package com.seulseul.seulseul.dto.android;

import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class RouteDetailWrapDto {
    private ArrayList<Object> transferSection;
    private ArrayList<Object> bodyList;
    private ArrayList<Object> totalTimeSection;

    public void setBodyList(RouteDetailDto routeDetailDto) {
        this.bodyList = new ArrayList<>(); // bodyList를 먼저 초기화

        String[] exNames = routeDetailDto.getExName();
        String[] laneNames = routeDetailDto.getLaneName();
        String[] wayNames = routeDetailDto.getWayName();

        int exSize = exNames.length;

        for (int i = 0; i < exSize + 1; i++) {
            Map<String, Object> bodyInfoMap = new LinkedHashMap<>();
            bodyInfoMap.put("viewType", "bodyInfo");

            Map<String, Object> dataMap = new LinkedHashMap<>();

            if (i == 0) {
                // 첫 번째 경우 firstStation 설정
                dataMap.put("firstStation", routeDetailDto.getFirstStation());
                dataMap.put("lastStation", exNames[i]);
                dataMap.put("laneName", laneNames[i]);
                dataMap.put("wayName", wayNames[i]);
            } else if (i == exSize) {
                // 마지막 경우 lastStation 설정
                dataMap.put("firstStation", exNames[i-1]);
                dataMap.put("lastStation", routeDetailDto.getLastStation());
                dataMap.put("laneName", laneNames[i]);
                dataMap.put("wayName", wayNames[i]);
            } else {
                // 그 외의 경우 이전역 -> 현재역 -> 다음역 순서로 표시
                dataMap.put("firstStation", exNames[i-1]);
                dataMap.put("lastStation", exNames[i]);
                dataMap.put("laneName", laneNames[i]);
                dataMap.put("wayName", wayNames[i]);
            }
            bodyInfoMap.put("data", dataMap);

            this.bodyList.add(bodyInfoMap);
        }
    }



    public void setTimeList(RouteDetailDto routeDetailDto) {
        this.totalTimeSection = new ArrayList<>(); // bodyList를 먼저 초기화

        Map<String, Object> titleMap = new LinkedHashMap<>();
        titleMap.put("viewType", "totalTimeSection");

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("totalTime", routeDetailDto.getTotalTime());
        map.put("startTime", routeDetailDto.getTimeList());
        map.put("arriveTime", routeDetailDto.getTimeList()); // 일단 넣어두기

        titleMap.put("data", map);
        this.totalTimeSection.add(titleMap);
    }

    public void setExWalkTimeList(RouteDetailDto routeDetailDto) {
        this.transferSection = new ArrayList<>(); // transferSection를 먼저 초기화

        String[] exWalkTime = routeDetailDto.getExWalkTime();

        int exWalkTimeSize = exWalkTime.length;

        for (int i = 0; i < exWalkTimeSize; i++) {
            Map<String, Object> exWalkTimeInfo = new LinkedHashMap<>();
            exWalkTimeInfo.put("viewType", "exWalkTimeInfo");

            Map<String, Object> dataMap = new LinkedHashMap<>();

            // 환승 지점의 대기 시간 설정
            dataMap.put("exWalkTime", exWalkTime[i]);

            exWalkTimeInfo.put("data", dataMap);

            this.transferSection.add(exWalkTimeInfo);
        }
    }
}