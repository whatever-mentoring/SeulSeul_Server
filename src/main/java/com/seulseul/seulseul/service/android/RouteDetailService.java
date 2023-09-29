package com.seulseul.seulseul.service.android;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seulseul.seulseul.dto.android.RouteDetailDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.entity.android.RouteDetail;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.stopTimeList.StopTimeList;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.repository.android.RouteDetailRepository;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import com.seulseul.seulseul.repository.stopTimeList.StopTimeListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Slf4j  //log.info() 사용가능
@Service
@Transactional(readOnly = true)
public class RouteDetailService {
    private final BaseRouteRepository baseRouteRepository;
    private final StopTimeListRepository stopTimeListRepository;
    private final RouteDetailRepository routeDetailRepository;

    @Transactional(readOnly = false)
    public RouteDetailDto routeDetailFromBaseRoute(Long id) throws JsonProcessingException {
        BaseRoute baseRoute = baseRouteRepository.findById(id).orElse(null);

        RouteDetailDto detailDto = new RouteDetailDto();
        //firstStation, lastStation, exName, exWalkTime, fastTrainDoor, laneName, wayName
        ObjectMapper objectMapper = new ObjectMapper();

//        String[] getLaneName = objectMapper.readValue(baseRoute.getLaneName(), String[].class);
//        String[] getWayName = objectMapper.readValue(baseRoute.getWayName(), String[].class);
//        String[] getTravelTime = objectMapper.readValue(baseRoute.getTravelTime(), String[].class);
//
//        String getLaneNameString = String.join(" ", getLaneName);
//        String getWayNameString = String.join(" ", getWayName);
//        String getTravelTimeString = String.join(" ", getTravelTime);

        if (baseRoute.getExSID1() != null) {
//            String[] getExName = objectMapper.readValue(baseRoute.getExName(), String[].class);
//            String[] getExWalkTime = objectMapper.readValue(baseRoute.getExWalkTime(), String[].class);
//            String[] getFastTrain = objectMapper.readValue(baseRoute.getFastTrainDoor(), String[].class);
//
//            String getExNameString = String.join(" ", getExName);
//            String getExWalkTimeString = String.join(" ", getExWalkTime);
//            String getFastTrainString = String.join(" ", getFastTrain);

            detailDto.updateFromBaseRoute(baseRoute.getFirstStation(), baseRoute.getLastStation(), baseRoute.getExName(), baseRoute.getExWalkTime(), baseRoute.getFastTrainDoor(), baseRoute.getLaneName(),baseRoute.getWayName(), baseRoute.getTravelTime());
        } else {
            detailDto.updateFromBaseRouteOnly(baseRoute.getFirstStation(), baseRoute.getLastStation(),baseRoute.getLaneName(),baseRoute.getWayName(), baseRoute.getTravelTime());
        }
        return detailDto;
    }

    @Transactional(readOnly = false)
    public List<String> compute(Long id) throws ParseException, IOException {
        //id로 stopTimeList 가져오기
        List<StopTimeList> stopTimeLists = stopTimeListRepository.findByBaseRouteId(id);
        BaseRoute baseRoute = baseRouteRepository.findById(id).orElse(null);

        //맨 뒤(도착역)부터
        StopTimeList lst;
        String time;
        String[] timeList2;
        List<String> resultTime = new ArrayList<>();
        String prev;
        String current;
        int index;
        int hours;
        int minutes;
        int h;
        int m;
        int transfer=0; //0인 경우 출발, 목적역 / 1인 경우 첫번째 환승역에서 내린 경우 / 2인 경우 환승역에서 exWalkTime 계산 후 타는 경우
        int travelTime;

        ObjectMapper objectMapper = new ObjectMapper();
        String[] getTravelTime = objectMapper.readValue(baseRoute.getTravelTime(), String[].class);
        List<Integer> getTravelTime2 = new ArrayList<>();
        for (String str : getTravelTime) {
            try {
                Integer intValue = Integer.parseInt(str);
                getTravelTime2.add(intValue);
            } catch (Exception e) {
                System.out.println("getTravelTime 오류"+e);
            }
        }

        //환승인 경우
        List<Integer> getExWalkTime2 = new ArrayList<>();
        int exWalkIdx = 0;  //초기화
        //laneName으로 환승 유무 확인
        String[] getLaneName = objectMapper.readValue(baseRoute.getLaneName(), String[].class);
        if (getLaneName.length != 1) {
            String[] getExWalkTime = objectMapper.readValue(baseRoute.getExWalkTime(), String[].class);

            for (String str : getExWalkTime) {
                try {
                    Integer intValue = Integer.parseInt(str);
                    getExWalkTime2.add(intValue);
                } catch (Exception e) {
                    System.out.println("getExWalkTime 오류"+e);
                }
            }
            exWalkIdx = getExWalkTime2.size()-1;
        }

        //도착역 -> 환승역(존재하는경우) -> 출발역
        for (int i=stopTimeLists.size()-1; i>=0; i--) {
            lst = stopTimeLists.get(i);
            time = lst.getTime();
            timeList2 = objectMapper.readValue(time, String[].class);

            index = timeList2.length-1;  //뒤에서 부터(stopTimeList에서 맨 뒤의 시간 index)

            if (i!=0 && i!=stopTimeLists.size()-1) {    //환승역인 경우
                transfer += 1;
            } else {                            //출발, 목적지역인 경우
                transfer = 0;
            }
            //도착역인경우
            if (resultTime.isEmpty()) {
                resultTime.add(timeList2[timeList2.length-1]);
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
                    minutes -= getExWalkTime2.get(exWalkIdx);
                    if (minutes < 0) {
                        minutes += 60;
                        hours -= 1;
                    }
                    exWalkIdx -= 1;
                }

                //역<->역 이동 시간 제외
                if (getLaneName.length != 1) {    //환승이 있을경우 exWalkIdx로 계산 가능
                    //getTravelTime 가져와서 minutes에서 제외!
                    travelTime = getTravelTime2.get(exWalkIdx+1);
                    minutes -= travelTime;
                    if (minutes < 0) {
                        minutes += 60;
                        hours -= 1;
                    }

                } else {    //환승이 없는 경우
                    travelTime = getTravelTime2.get(i);
                    minutes -= travelTime;
                    if (minutes < 0) {
                        minutes += 60;
                        hours -= 1;
                    }
                }

                //현재역과 직전역의 시간 비교
                while (true) {
                    //현재 역에서의 시간
                    current = timeList2[index];
                    // 콜론을 기준으로 문자열을 분리
                    String[] p = current.split(":");
                    // 분리된 문자열을 정수로 변환
                    h = Integer.parseInt(p[0]);
                    m = Integer.parseInt(p[1]);
                    if (h<hours || (h==hours && m<=minutes)) {
                        resultTime.add(timeList2[index]);
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

    @Transactional(readOnly = false)
    public String checkTimeList(Long id, List<String> timeList) throws ParseException, JsonProcessingException {
        //id로 stopTimeList 가져오기
        List<StopTimeList> stopTimeLists = stopTimeListRepository.findByBaseRouteId(id);
        BaseRoute baseRoute = baseRouteRepository.findById(id).orElse(null);

        //맨 처음(출발역)부터
        StopTimeList lst;
        String time;
        String[] timeList2;
        List<String> resultTime = new ArrayList<>();
        List<String> originalTimeList = timeList;
        String prev;
        String current;
        int hours;
        int minutes;
        int h;
        int m;
        int transfer=0; //0인 경우 출발, 목적역 / 1인 경우 첫번째 환승역에서 내린 경우 / 2인 경우 환승역에서 exWalkTime 계산 후 타는 경우
        int exWalkIdx = 0;
        int travelTime;
        int check=0;
        int timeIdx;
        int travelIdx=0;

        ObjectMapper objectMapper = new ObjectMapper();
        String[] getTravelTime = objectMapper.readValue(baseRoute.getTravelTime(), String[].class);
        List<Integer> getTravelTime2 = new ArrayList<>();
        for (String str : getTravelTime) {
            try {
                Integer intValue = Integer.parseInt(str);
                getTravelTime2.add(intValue);
            } catch (Exception e) {
                System.out.println("getTravelTime 오류"+e);
            }
        }

        //환승인 경우
        List<Integer> getExWalkTime2 = new ArrayList<>();
        //laneName으로 환승 유무 확인
        String[] getLaneName = objectMapper.readValue(baseRoute.getLaneName(), String[].class);
        if (getLaneName.length != 1) {
            String[] getExWalkTime = objectMapper.readValue(baseRoute.getExWalkTime(), String[].class);

            for (String str : getExWalkTime) {
                try {
                    Integer intValue = Integer.parseInt(str);
                    getExWalkTime2.add(intValue);
                } catch (Exception e) {
                    System.out.println("getExWalkTime 오류"+e);
                }
            }
            exWalkIdx = 0;
        }

        //출발역 -> 환승역(존재하는경우) -> 도착역
        //앞에서 계산한 timeList 시간과 lst에 있는 가장 가까운 시간 비교
        for (int i=0; i<stopTimeLists.size(); i++) {    //i는 0부터 (stopTimeLists의 크기-1)까지. 환승1번=>i:0,1,2,3

            lst = stopTimeLists.get(i);
            time = lst.getTime();
            timeList2 = objectMapper.readValue(time, String[].class);   //timeList2는 해당 stopTimeList의 시간 String[] 타입

            if (i!=0 && i!=stopTimeLists.size()-1) {    //환승역인 경우
                transfer += 1;
            } else {                            //출발, 목적지역인 경우
                transfer = 0;
            }

            //출발역인 경우
            if (resultTime.isEmpty()) { //출발지에서 출발시간인 경우 resultTime에 추가
                resultTime.add(originalTimeList.get(0));
            } else {
                prev = resultTime.get(resultTime.size()-1); //이전역에서 출발한 시간
                String[] parts = prev.split(":");

                // 분리된 문자열을 정수로 변환
                hours = Integer.parseInt(parts[0]);
                minutes = Integer.parseInt(parts[1]);

                if (getLaneName.length != 1) {
                    //내리는 경우 => travelTime 더하고 그 시간과 가장 가까운 시간 구하기(1 앞뒤로는 인정)
                    if (check%2 == 0) {
                        minutes += getTravelTime2.get(travelIdx);
                        if (minutes >= 60) {
                            if (minutes > 60) {
                                minutes -= 60;
                                hours += 1;
                            }
                        }
                        if (travelIdx+1 != getTravelTime2.size()) {
                            travelIdx += 1;
                        }
                    }

                    //올라가는 경우 => exWalkTime 구하고 그 시간과 가장 가까운 다음 시간 구하기
                    if (check%2 == 1) {
                        minutes += getExWalkTime2.get(exWalkIdx);
                        if (minutes > 60) {
                            minutes -= 60;
                            hours += 1;
                        }
                        if (exWalkIdx+1 != getExWalkTime2.size()) {
                            exWalkIdx += 1;
                        }
                    }
                    check += 1;

                //애초에 exWalkTime이 없는 경우
                } else {
                    travelTime = getTravelTime2.get(i-1);
                    minutes += travelTime;
                    if (minutes < 0) {
                        minutes -= 60;
                        hours += 1;
                    }
                }

                timeIdx = 0;
                //현재역과 직전역의 시간 비교
                while (true) {
                    //현재 역에서의 시간
                    current = timeList2[timeIdx];

                    // 콜론을 기준으로 문자열을 분리
                    String[] p = current.split(":");
                    // 분리된 문자열을 정수로 변환
                    h = Integer.parseInt(p[0]);
                    m = Integer.parseInt(p[1]);

                    if (h>hours || (h==hours && m>=minutes)) {
                        resultTime.add(timeList2[timeIdx]);
                        break;
                    } else if(check%2==1 && h==hours && m+1==minutes) { //내리는 경우 1분의 오차 존재 가능성 있음
                        resultTime.add(timeList2[timeIdx]);
                        break;
                    } else {
                        timeIdx += 1;
                    }
                }
            }
        }

        String resultT = objectMapper.writeValueAsString(resultTime);

        return resultT;
    }

    @Transactional
    public RouteDetailDto updateTimeList(Long id, RouteDetailDto detailDto, String timeList) throws JsonProcessingException {
        BaseRoute baseRoute = baseRouteRepository.findById(id).orElse(null);

        ObjectMapper objectMapper = new ObjectMapper();

        String[] getTimeList = objectMapper.readValue(timeList,String[].class);

        //firstStation, lastStation, exName, exWalkTime, fastTrainDoor, laneName, wayName
        String startTime = getTimeList[0];
        String endTime = getTimeList[getTimeList.length-1];

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
            resultH -= 1;
        }
        String totalTime = "";
        if (resultH == 0) {
            totalTime = resultM+"분";
        }
        else {
            totalTime = resultH+"시간 "+resultM+"분";

        }
        String resultT = objectMapper.writeValueAsString(timeList);

        detailDto.updateTimeList(resultT, totalTime);
        System.out.println(detailDto);
        return detailDto;
    }

    @Transactional
    public RouteDetail saveRouteDetail(RouteDetailDto dto, BaseRoute baseRoute) {
        RouteDetail routeDetail = routeDetailRepository.save(new RouteDetail(dto));
//        RouteDetail routeDetail = new RouteDetail();
//        Long id = baseRoute.getId();
//        routeDetail.saveRouteDetail(id, dto);
        // BaseRoute에 routeDetail 저장
        baseRoute.saveRouteDetail(routeDetail);
        return routeDetail;
    }
}