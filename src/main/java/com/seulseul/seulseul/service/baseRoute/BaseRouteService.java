package com.seulseul.seulseul.service.baseRoute;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteTransferDto;
import com.seulseul.seulseul.entity.ApiKey;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

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
public class BaseRouteService {
    private final ApiKey apiService;
    private final BaseRouteRepository baseRouteRepository;

    @Transactional
    public String getFromAPI(int SID, int EID) throws IOException {
        //1. API 연결

        String urlInfo = "https://api.odsay.com/v1/api/subwayPath?lang=0&CID=1000&SID="+ SID +"&EID="+ EID +"&Sopt=2&apiKey=" + URLEncoder.encode(apiService.getApiKey(), "UTF-8");

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

        log.info("sb.toString() :", sb.toString());
        System.out.println(sb.toString());

        return sb.toString();
    }

    @Transactional
    public BaseRoute findTransferData(Long id) throws IOException {
        //기존에 존재하는 baseRoute id로 해당 row 찾기
        BaseRoute baseRoute = baseRouteRepository.findById(id).orElse(null);
        System.out.println("id:"+id);
        System.out.println("baseRoute:"+ baseRoute);
        //미리 저장된 출발역과 도착역 정보를 넣어 API 받기
        String string = getFromAPI(baseRoute.getSID(), baseRoute.getEID());
        //원하는 데이터 찾기
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(string); // jsonString은 JSON 문자열을 담고 있는 변수

            //각 배열에 접근
            JsonNode driveInfoArray = jsonNode.get("result").get("driveInfoSet").get("driveInfo");
            JsonNode exChangeInfoArray = jsonNode.get("result").get("exChangeInfoSet").get("exChangeInfo");

            // "exSID" 값을 저장할 리스트 생성
            List<Integer> exSIDList = new ArrayList<>();    //환승역 stationId
            List<String> laneNameList = new ArrayList<>();  //몇호선(ex. 9호선-> 1호선)
            List<Integer> wayCodeList = new ArrayList<>();  //wayCode(방면코드. 1:상행 2:하행)
            List<String> wayNameList = new ArrayList<>();   //방면명
            List<Integer> fastDoorList = new ArrayList<>(); //최소환승
            List<Integer> exWalkTimeList = new ArrayList<>();//환승 역 사이의 소요시간
            List<String> exNameList = new ArrayList<>();    //환승역 이름

            // driveInfoArray 내의 데이터
            for (JsonNode driveInfo : driveInfoArray) {
                String laneName = driveInfo.get("laneName").asText();
                Integer wayCode = driveInfo.get("wayCode").asInt();
                String wayName = driveInfo.get("wayName").asText();

                laneNameList.add(laneName);
                wayCodeList.add(wayCode);
                wayNameList.add(wayName);

            }
            // "exChangeInfo" 배열을 반복하면서 "exSID" 값을 추출하여 리스트에 추가
            for (JsonNode exChangeInfo : exChangeInfoArray) {
                String exName = exChangeInfo.get("exName").asText();
                Integer exSID = exChangeInfo.get("exSID").asInt();
                Integer fastDoor = exChangeInfo.get("fastDoor").asInt();
                Integer exWalkTime = exChangeInfo.get("exWalkTime").asInt();

                exNameList.add(exName);
                exSIDList.add(exSID);
                fastDoorList.add(fastDoor);
                exWalkTimeList.add(exWalkTime);
            }

            //객체에 넣어서 실제 DB에 저장
            baseRoute.update(laneNameList, wayCodeList, wayNameList, exNameList, exSIDList, fastDoorList, exWalkTimeList);

            baseRouteRepository.save(baseRoute);

        } catch (Exception e) {
            log.info(e.toString());
        }
        return baseRoute;
    }




}