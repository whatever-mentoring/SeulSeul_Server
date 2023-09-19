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

        System.out.println(sb.toString());

        return sb.toString();
    }

    // 데이터 저장 => 일단 출발역만!!!!!!. 변하는것: 역Id, wayCode에따라up/down, 요일에따라OrdList/SatList/SunList
    //[시작역,환승역1,도착역], [2,1]
    @Transactional
    public StopTimeList findStopTimeListData(Long id) throws IOException {
        //출발역, 도착역, 환승역 stationId
        List<Integer> stationIdList = new ArrayList<>();
        // "exSID" 값을 저장할 리스트 생성
        List<String> timeList = new ArrayList<>();

        //기존에 존재하는 baseRoute id로 해당 row 찾기
        BaseRoute baseRoute = baseRouteRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BASEROUTE_NOT_FOUND));
        //거치는 모든 역의 stationId 순서대로 :출발->환승->도착
        stationIdList.add(baseRoute.getSID());
        for (int i=0; i<baseRoute.getExSID1().size(); i++) {
            stationIdList.add(baseRoute.getExSID1().get(i));
            stationIdList.add(baseRoute.getExSID2().get(i));
        }
        stationIdList.add(baseRoute.getEID());
        System.out.println("stationId" + stationIdList);
        //미리 저장된 출발역과 도착역 정보를 넣어 API 받기

//        for (Integer stationId : stationIdList) {
//
//        }
        String string = getStopTimeListFromAPI(baseRoute.getSID(), baseRoute.getWayCode().get(0));

        //원하는 데이터 찾기
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(string); // jsonString은 JSON 문자열을 담고 있는 변수
            //각 배열에 접근
            JsonNode ordArray = jsonNode.get("result").get("OrdList").get("down").get("time");
//            JsonNode satArray = jsonNode.get("result").get("SatList").get("up").get("time");
//            JsonNode sunArray = jsonNode.get("result").get("SunList").get("up").get("time");


            int totalElements = ordArray.size(); // ordArray의 총 요소 개수
            int startIndex = Math.max(totalElements - 5, 0); // 뒤에서 5개 요소의 시작 인덱스 계산

            for (int i = startIndex; i < totalElements; i++) {
                JsonNode ordInfo = ordArray.get(i);
                Integer hour = ordInfo.get("Idx").asInt();
                String minutes = ordInfo.get("list").asText();
                String[] minute = minutes.split(" ");

                for (int j=0; j<minute.length; j++) {
                    timeList.add(hour + "-" + minute[j]);
                }
            }
            //객체에 넣어서 실제 DB에 저장
            StopTimeList stopTimeList = new StopTimeList(timeList);
            stopTimeListRepository.save(stopTimeList);

        } catch (Exception e) {
            log.info(e.toString());
        }
        return stopTimeList;
    }
}
