package com.seulseul.seulseul.service.stopTimeList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seulseul.seulseul.config.CustomException;
import com.seulseul.seulseul.config.ErrorCode;
import com.seulseul.seulseul.entity.ApiKey;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.stopTimeList.StopTimeList;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import com.seulseul.seulseul.repository.stopTimeList.StopTimeListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Slf4j  //log.info() 사용가능
@Service
@Transactional(readOnly = true)
public class StopTimeListService {
    private final ApiKey apiKey;
    private StopTimeList stopTimeList;
    private final BaseRouteRepository baseRouteRepository;
    private final StopTimeListRepository stopTimeListRepository;

    // Odsay에서 데이터 가져오기
    @Transactional(readOnly = false)
    public String getStopTimeListFromAPI(int stationId, int wayCode) throws IOException {

        //1. API 연결
        String urlInfo = "https://api.odsay.com/v1/api/subwayTimeTable?lang=0&stationID="+ stationId +"&wayCode="+ wayCode +"&apiKey=" + URLEncoder.encode(apiKey.getApiKey(), "UTF-8");

        // http 연결
        URL url = new URL(urlInfo);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        bufferedReader.close();
        conn.disconnect();

        return sb.toString();
    }

    // 데이터 저장 => 일단 출발역만!!!!!!. 변하는것: 역Id, wayCode에따라up/down, 요일에따라OrdList/SatList/SunList
    //[시작역,환승역1,도착역], [2,1]
    @Transactional
    public StopTimeList findStopTimeListData(Long id) throws IOException {
        //출발역, 도착역, 환승역 stationId
        List<Integer> stationIdList = new ArrayList<>();

        //방면
        String wayName;

        //기존에 존재하는 baseRoute id로 해당 row 찾기
        BaseRoute baseRoute = baseRouteRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BASEROUTE_NOT_FOUND));

        //거치는 모든 역의 stationId 순서대로 :출발id->환승id->(동일한 환승역이름 But 환승id 다름)환승id->도착
        stationIdList.add(baseRoute.getSID());
        for (int i=0; i<baseRoute.getExSID1().size(); i++) {
            stationIdList.add(baseRoute.getExSID1().get(i));
            stationIdList.add(baseRoute.getExSID2().get(i));
        }
        stationIdList.add(baseRoute.getEID());

        //기존의 값이 존재하는 경우 삭제
        if (!stopTimeListRepository.findByBaseRouteId(id).isEmpty()) {
            List<StopTimeList> stopTimeList1 = stopTimeListRepository.findByBaseRouteId(id);
            for (int i=0;i<stopTimeList1.size();i++) {
                stopTimeListRepository.delete(stopTimeList1.get(i));
            }
        }

        //미리 저장된 출발역과 도착역 정보를 넣어 API 받기
        int check = 0;
        int idx = 0;
        for (Integer stationId : stationIdList) {
            if (check == 2) {
                idx += 1;
                check = 0;
            }
            //API 사용
            String string = getStopTimeListFromAPI(stationId, baseRoute.getWayCode().get(idx));
            //wayName 방면
            wayName = baseRoute.getWayName().get(idx);

            check += 1;

            //원하는 데이터 찾기
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                JsonNode jsonNode = objectMapper.readTree(string); // jsonString은 JSON 문자열을 담고 있는 변수
                // "exSID" 값을 저장할 리스트 생성
                List<String> timeList = new ArrayList<>();

                //각 배열에 접근
                JsonNode ordArray;
                if (baseRoute.getWayCode().get(idx) == 1) {
                    if (baseRoute.getDayInfo().equals("토요일")) {
                        ordArray = jsonNode.get("result").get("SatList").get("up").get("time");
                    } else if (baseRoute.getDayInfo().equals("일요일")) {
                        ordArray = jsonNode.get("result").get("SunList").get("up").get("time");
                    } else {
                        ordArray = jsonNode.get("result").get("OrdList").get("up").get("time");
                    }
                } else {
                    if (baseRoute.getDayInfo().equals("토요일")) {
                        ordArray = jsonNode.get("result").get("SatList").get("down").get("time");
                    } else if (baseRoute.getDayInfo().equals("일요일")) {
                        ordArray = jsonNode.get("result").get("SunList").get("down").get("time");
                    } else {
                        ordArray = jsonNode.get("result").get("OrdList").get("down").get("time");
                    }
                }

                //가져온 값을 뒤에서 5시간까지만 받아오기
                int totalElements = ordArray.size(); // ordArray의 총 요소 개수
                int startIndex = Math.max(totalElements - 5, 0); // 뒤에서 5개 요소의 시작 인덱스 계산

                for (int i = startIndex; i < totalElements; i++) {
                    JsonNode ordInfo = ordArray.get(i);
                    Integer hour = ordInfo.get("Idx").asInt();
                    String minutes = ordInfo.get("list").asText();
                    String[] minute = minutes.split(" ");

                    //wayName에 해당하는 시간만 DB에 저장
                    if (wayName.contains(".")) {
                        // 정규표현식 패턴 설정
                        Pattern pattern = Pattern.compile("\\.");
                        String[] parts = pattern.split(wayName);

                        // 문자열을 점(".")을 기준으로 분할한 결과를 배열로 저장
                        for (String part : parts) {
                            for (String m : minute) {
                                if (m.contains(part)) {
                                    String min = m.split("\\(")[0];
                                    timeList.add(hour + "-" + min);
                                }
                            }
                        }

                    } else {
                        for (String m : minute) {
                            if (m.contains(wayName)) {
                                String min = m.split("\\(")[0];
                                timeList.add(hour + "-" + min);
                            }
                        }
                    }
                }
                //객체에 넣어서 실제 DB에 저장
                StopTimeList stopTimeList = new StopTimeList();
                stopTimeList.update(id, stationId, timeList);
                stopTimeListRepository.save(stopTimeList);

            } catch (Exception e) {
                log.info(e.toString());
            }
        }

        return stopTimeList;
    }
}
