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
    public static String[] extractTimes(String input) {
        String cleaned = input.replace("\"[\\\"", "").replace("\\\"]\"", "").replace("\\\",\\\"", ",");
        String[] times = cleaned.split(",");
        return times;
    }

    public void setBodyList(RouteDetailDto routeDetailDto) {
        this.bodyList = new ArrayList<>(); // bodyList를 먼저 초기화

        String[] exNames = routeDetailDto.getExName();
        String[] laneNames = routeDetailDto.getLaneName();
        String[] wayNames = routeDetailDto.getWayName();
        String[] timeList = extractTimes(routeDetailDto.getTimeList());
        String[] fastTrainDoors = routeDetailDto.getFastTrainDoor();

        int exSize = exNames.length;
        int timeSize = timeList.length;
        int fastTrainDoorSize = fastTrainDoors.length;

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
                dataMap.put("departTime", timeList[0]);
                dataMap.put("arriveTime", timeList[1]);
                dataMap.put("fastTrainDoor", fastTrainDoors[0]);
            } else if (i == exSize) {
                // 마지막 경우 lastStation 설정
                dataMap.put("firstStation", exNames[i-1]);
                dataMap.put("lastStation", routeDetailDto.getLastStation());
                dataMap.put("laneName", laneNames[i]);
                dataMap.put("wayName", wayNames[i]);
                dataMap.put("departTime", timeList[timeSize-1]);
            } else {
                // 그 외의 경우 이전역 -> 현재역 -> 다음역 순서로 표시
                dataMap.put("firstStation", exNames[i-1]);
                dataMap.put("lastStation", exNames[i]);
                dataMap.put("laneName", laneNames[i]);
                dataMap.put("wayName", wayNames[i]);
                dataMap.put("departTime", timeList[timeSize-3]);
                dataMap.put("arriveTime", timeList[timeSize-2]);
                dataMap.put("fastTrainDoor", fastTrainDoors[fastTrainDoorSize-1]);
            }
            bodyInfoMap.put("data", dataMap);

            this.bodyList.add(bodyInfoMap);
        }
    }

    public void setTimeList(RouteDetailDto routeDetailDto) {
        this.totalTimeSection = new ArrayList<>(); // bodyList를 먼저 초기화

        String[] timeList = extractTimes(routeDetailDto.getTimeList());
        int timeSize = timeList.length;

        Map<String, Object> titleMap = new LinkedHashMap<>();
        titleMap.put("viewType", "totalTimeSection");

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("totalTime", routeDetailDto.getTotalTime());
        map.put("departTime", timeList[0]);
        map.put("arriveTime", timeList[timeSize-1]);
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