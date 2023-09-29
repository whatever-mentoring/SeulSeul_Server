package com.seulseul.seulseul.dto.android;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class RouteDetailWrapDto {
    private ArrayList<Object> bodyList;
    private ArrayList<Object> totalTimeSection;

    public static String[] extractTimes(String input) {
        String cleaned = input.replace("\"[\\\"", "").replace("\\\"]\"", "").replace("\\\",\\\"", ",");
        String[] times = cleaned.split(",");
        return times;
    }

    public void setBodyExList(RouteDetailDto routeDetailDto) throws JsonProcessingException {
        this.bodyList = new ArrayList<>(); // bodyList를 먼저 초기화

        ObjectMapper objectMapper = new ObjectMapper();

        String[] exNames = objectMapper.readValue(routeDetailDto.getExName(), String[].class);
        String[] exWalkTime = objectMapper.readValue(routeDetailDto.getExWalkTime(), String[].class);
        String[] laneNames = objectMapper.readValue(routeDetailDto.getLaneName(), String[].class);
        String[] wayNames = objectMapper.readValue(routeDetailDto.getWayName(), String[].class);
        String[] fastTrainDoors = objectMapper.readValue(routeDetailDto.getFastTrainDoor(), String[].class);
        String[] timeList = extractTimes(routeDetailDto.getTimeList());

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
                dataMap.put("departTime", timeList[timeSize-2]);
                dataMap.put("arriveTime", timeList[timeSize-1]);
            } else {
                // 그 외의 경우 이전역 -> 현재역 -> 다음역 순서로 표시
                dataMap.put("firstStation", exNames[i-1]);
                dataMap.put("lastStation", exNames[i]);
                dataMap.put("laneName", laneNames[i]);
                dataMap.put("wayName", wayNames[i]);
                dataMap.put("departTime", timeList[timeSize-4]);
                dataMap.put("arriveTime", timeList[timeSize-3]);
                dataMap.put("fastTrainDoor", fastTrainDoors[fastTrainDoorSize-1]);
            }
            bodyInfoMap.put("data", dataMap);

            this.bodyList.add(bodyInfoMap);

            if (i < exSize) {
                Map<String, Object> walkTimeInfoMap = new LinkedHashMap<>();
                walkTimeInfoMap.put("viewType", "exWalkTimeInfo");

                Map<String, Object> walkDataMap = new LinkedHashMap<>();
                walkDataMap.put("exWalkTime", exWalkTime[i]);

                walkTimeInfoMap.put("data", walkDataMap);

                this.bodyList.add(walkTimeInfoMap);
            }
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

    public void setBodyList(RouteDetailDto routeDetailDto) {
        this.bodyList = new ArrayList<>(); // bodyList를 먼저 초기화
        String[] timeList = extractTimes(routeDetailDto.getTimeList());


        int timeSize = timeList.length;

        String[] exWalkTime = new String[]{routeDetailDto.getExWalkTime()};


        Map<String, Object> dataMap = new LinkedHashMap<>();
        dataMap.put("viewType", "bodyInfo");

        Map<String, Object> map = new LinkedHashMap<>();

        String[] getWayName = new String[]{routeDetailDto.getWayName()};
        String[] getLaneName = new String[]{routeDetailDto.getLaneName()};

        map.put("firstStation", routeDetailDto.getFirstStation());
        map.put("lastStation", routeDetailDto.getLastStation());
        map.put("laneName", getLaneName[0]);
        map.put("wayName", getWayName[0]);
        map.put("departTime", timeList[timeSize-2]);
        map.put("arriveTime", timeList[timeSize-1]);
        dataMap.put("data", map);
        this.bodyList.add(dataMap);

    }
}