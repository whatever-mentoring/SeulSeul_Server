package com.seulseul.seulseul.dto.android;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class RouteDetailWrapDto {
    private ArrayList<Object> exWalkTimeList;
    private ArrayList<Object> bodyList;
    private ArrayList<Object> timeList;

    public void setBodyList(RouteDetailDto routeDetailDto) {
        this.bodyList = new ArrayList<>(); // bodyList를 먼저 초기화

        Map<String, Object> titleMap = new LinkedHashMap<>();
        titleMap.put("viewType", "bodyInfo");

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("firstStation", routeDetailDto.getFirstStation());
        map.put("lastStation", routeDetailDto.getLastStation());
        map.put("exName", routeDetailDto.getExName());
        map.put("laneName", routeDetailDto.getLaneName());
        map.put("fastTrainDoor", routeDetailDto.getFastTrainDoor());
        map.put("wayName", routeDetailDto.getWayName());

        titleMap.put("data", map);
        this.bodyList.add(titleMap);
    }

    public void setTimeList(RouteDetailDto routeDetailDto) {
        this.timeList = new ArrayList<>(); // bodyList를 먼저 초기화

        Map<String, Object> titleMap = new LinkedHashMap<>();
        titleMap.put("viewType", "timeInfo");

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("totalTime", routeDetailDto.getTotalTime());
        map.put("startTime", routeDetailDto.getTimeList().get(0));
        map.put("arriveTime", routeDetailDto.getTimeList().get(0)); // 일단 넣어두기

        titleMap.put("data", map);
        this.timeList.add(titleMap);
    }

    public void setExWalkTimeList(RouteDetailDto routeDetailDto) {
        this.exWalkTimeList = new ArrayList<>(); // bodyList를 먼저 초기화

        Map<String, Object> titleMap = new LinkedHashMap<>();
        titleMap.put("viewType", "exWalkTimeInfo");

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("exWalkTime", routeDetailDto.getExWalkTime());

        titleMap.put("data", map);
        this.exWalkTimeList.add(titleMap);
    }
}
