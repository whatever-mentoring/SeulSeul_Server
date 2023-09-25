package com.seulseul.seulseul.dto.android;

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

        List<String> exNames = routeDetailDto.getExName();
        int exSize = exNames.size();

        for (int i = 0; i < exSize + 1; i++) {
            Map<String, Object> bodyInfoMap = new LinkedHashMap<>();
            bodyInfoMap.put("viewType", "bodyInfo");

            Map<String, Object> dataMap = new LinkedHashMap<>();

            if (i == 0) {
                // 첫 번째 경우 firstStation 설정
                dataMap.put("firstStation", routeDetailDto.getFirstStation());
                dataMap.put("lastStation", exNames.get(i));
            } else if (i == exSize) {
                // 마지막 경우 lastStation 설정
                dataMap.put("firstStation", exNames.get(i - 1));
                dataMap.put("lastStation", routeDetailDto.getLastStation());
            } else {
                // 그 외의 경우 이전역 -> 현재역 -> 다음역 순서로 표시
                dataMap.put("firstStation", exNames.get(i - 1));
                dataMap.put("lastStation", exNames.get(i));
            }

            // 나머지 정보 추가
            dataMap.put("laneName", routeDetailDto.getLaneName());
            dataMap.put("fastTrainDoor", routeDetailDto.getFastTrainDoor());
            dataMap.put("wayName", routeDetailDto.getWayName());

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
        map.put("startTime", routeDetailDto.getTimeList().get(0));
        map.put("arriveTime", routeDetailDto.getTimeList().get(0)); // 일단 넣어두기

        titleMap.put("data", map);
        this.totalTimeSection.add(titleMap);
    }

    public void setExWalkTimeList(RouteDetailDto routeDetailDto) {
        this.transferSection = new ArrayList<>(); // bodyList를 먼저 초기화

        Map<String, Object> titleMap = new LinkedHashMap<>();
        titleMap.put("viewType", "exWalkTimeInfo");

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("exWalkTime", routeDetailDto.getExWalkTime());

        titleMap.put("data", map);
        this.transferSection.add(titleMap);
    }
}