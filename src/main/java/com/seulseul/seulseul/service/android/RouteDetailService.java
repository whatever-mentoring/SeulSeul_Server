package com.seulseul.seulseul.service.android;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Transactional(readOnly = false)
    public RouteDetailDto routeDetailFromBaseRoute(Long id) throws JsonProcessingException {
        BaseRoute baseRoute = baseRouteRepository.findById(id).orElse(null);

        RouteDetailDto detailDto = new RouteDetailDto();
        //firstStation, lastStation, exName, exWalkTime, fastTrainDoor, laneName, wayName
        ObjectMapper objectMapper = new ObjectMapper();

        String[] getLaneName = objectMapper.readValue(baseRoute.getLaneName(), String[].class);
        String[] getWayName = objectMapper.readValue(baseRoute.getWayName(), String[].class);

        if (baseRoute.getExSID1() != null) {
            String[] getExName = objectMapper.readValue(baseRoute.getExName(), String[].class);
            String[] getExWalkTime = objectMapper.readValue(baseRoute.getExWalkTime(), String[].class);
            String[] getFastTrain = objectMapper.readValue(baseRoute.getFastTrainDoor(), String[].class);

            detailDto.updateFromBaseRoute(baseRoute.getFirstStation(), baseRoute.getLastStation(),getExName, getExWalkTime, getFastTrain, getLaneName, getWayName);
        } else {
            detailDto.updateFromBaseRouteOnly(getLaneName, getWayName);
        }

        return detailDto;
    }

    @Transactional(readOnly = false)
    public List<String> compute(Long id) throws ParseException, IOException {
        //id로 stopTimeList 가져오기
        List<StopTimeList> stopTimeLists = stopTimeListRepository.findByBaseRouteId(id);
        BaseRoute baseRoute = baseRouteRepository.findById(id).orElse(null);

        ObjectMapper objectMapper = new ObjectMapper();
        String[] getExWalkTime = objectMapper.readValue(baseRoute.getExWalkTime(), String[].class);
        List<Integer> getExWalkTime2 = new ArrayList<>();
        for (String str : getExWalkTime) {
            try {
                Integer intValue = Integer.parseInt(str);
                getExWalkTime2.add(intValue);
            } catch (Exception e) {
                System.out.println("올바른 숫자가 아님"+e);
            }
        }

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
        int exWalkIdx = getExWalkTime2.size()-1;

        for (int i=stopTimeLists.size()-1; i>=0; i--) {
            lst = stopTimeLists.get(i);
            time = lst.getTime();
            timeList2 = objectMapper.readValue(time, String[].class);
            index = timeList2.length-1;  //뒤에서 부터(stopTimeList에서 맨 뒤의 시간 index)
            System.out.println("time"+time);
            if (i!=0 && i!=stopTimeLists.size()-1) {
                transfer += 1;
            } else {
                transfer = 0;
            }

            //도착역인경우
            if (resultTime.isEmpty()) {
                resultTime.add(timeList2[index]);
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

        System.out.println("update"+result);
        return result;
    }

    @Transactional(readOnly = false)
    public String checkTimeList(Long id, List<String> timeList) throws ParseException, JsonProcessingException {
        //id로 stopTimeList 가져오기
        List<StopTimeList> stopTimeLists = stopTimeListRepository.findByBaseRouteId(id);
        BaseRoute baseRoute = baseRouteRepository.findById(id).orElse(null);

        ObjectMapper objectMapper = new ObjectMapper();

        String[] getExWalkTim = objectMapper.readValue(baseRoute.getExWalkTime(), String[].class);
        List<Integer> getExWalkTime = new ArrayList<>();
        for (String str:getExWalkTim) {
            try {
                Integer intValue = Integer.parseInt(str);
                getExWalkTime.add(intValue);
            } catch (Exception e) {
                System.out.println(e+"올바른 숫자 형식이 아님");
            }
        }

        //맨 처음(출발역)부터
        StopTimeList lst;
        String time;
        String[] timeList2;
        List<String> resultTime = new ArrayList<>();
        List<String> originalTimeList = timeList;
        String prev;
        String current;
        int index;
        int hours;
        int minutes;
        int h;
        int m;
        int transfer=0; //0인 경우 출발, 목적역 / 1인 경우 첫번째 환승역에서 내린 경우 / 2인 경우 환승역에서 exWalkTime 계산 후 타는 경우
        int exWalkIdx = 0;

        //앞에서 계산한 timeList 시간과 lst에 있는 가장 가까운 시간 비교
        for (int i=0; i<stopTimeLists.size()-1; i++) {
            lst = stopTimeLists.get(i);
            time = lst.getTime();
            timeList2 = objectMapper.readValue(time, String[].class);
            index = 0;

            if (i!=0 && i!=stopTimeLists.size()-1) {
                transfer += 1;
            } else {
                transfer = 0;
            }

            if (i==0) { //출발지에서 출발시간인 경우 resultTime에 추가
                resultTime.add(originalTimeList.get(0));
            } else {
                prev = resultTime.get(i-1); //이전역에서 출발한 시간
                String[] parts = prev.split(":");

                // 분리된 문자열을 정수로 변환
                hours = Integer.parseInt(parts[0]);
                minutes = Integer.parseInt(parts[1]);

                if (transfer == 2) {
                    minutes += getExWalkTime.get(exWalkIdx);
                    if (minutes > 60) {
                        minutes -= 60;
                        hours += 1;
                    }
                    exWalkIdx += 1;
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
                    if (h>hours || (h==hours && m>minutes)) {
                        resultTime.add(timeList2[index]);
                        break;
                    } else {
                        index += 1;
                    }
                }
            }
        }
        System.out.println("checkTeamList resultTime: "+resultTime);
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
        }

        String totalTime = resultH+"시간 "+resultM+"분";

        String resultT = objectMapper.writeValueAsString(timeList);

        detailDto.updateTimeList(resultT, totalTime);

        return detailDto;
    }
}
