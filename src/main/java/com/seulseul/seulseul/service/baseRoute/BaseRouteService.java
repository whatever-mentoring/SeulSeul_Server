package com.seulseul.seulseul.service.baseRoute;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.seulseul.seulseul.config.CustomException;
import com.seulseul.seulseul.config.ErrorCode;
import com.seulseul.seulseul.dto.alarm.AlarmDto;
import com.seulseul.seulseul.dto.alarm.AlarmReqDto;
import com.seulseul.seulseul.dto.baseRoute.*;
import com.seulseul.seulseul.dto.firebase.FCMDto;
import com.seulseul.seulseul.entity.ApiKey;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
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
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j  //log.info() 사용가능
@Service
@Transactional(readOnly = true)
public class BaseRouteService {
    private final ApiKey apiKey;
    private final BaseRouteRepository baseRouteRepository;


    // Odsay API 불러오기
    public String getUrl(double startX, double startY, double endX, double endY) throws IOException{
        String urlInfo = "https://api.odsay.com/v1/api/searchPubTransPathT?lang=0&SX=" + startX + "&SY=" + startY + "&EX="
                + endX + "&EY=" + endY + "&SearchPathType=1&apiKey="
                + URLEncoder.encode(apiKey.getApiKey(), "UTF-8");
        return urlInfo;
    }

    public String getJson(double startX, double startY, double endX, double endY) throws IOException {
        // http 연결
        String urlInfo = getUrl(startX, startY, endX, endY);
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

        String jsonString = sb.toString();
        return jsonString;
    }

    // 현재 위치(좌표), 요일 받아오기
    @Transactional
    public BaseRouteStartDto saveStartInfo(BaseRouteStartReqDto reqDto, User user) {
        // 알림 테이블 정보 + User 정보로 원하는 디비 row 찾기
        BaseRoute baseRoute = baseRouteRepository.findByIdAndUser(reqDto.getId(), user)
                .orElseThrow(() -> new CustomException(ErrorCode.BASEROUTE_NOT_FOUND));
        baseRoute.saveStartInfo(reqDto.getStartX(), reqDto.getStartY(), reqDto.getDayInfo());
        return new BaseRouteStartDto(baseRoute.getId(), reqDto.getStartX(), reqDto.getStartY(), reqDto.getDayInfo());
    }

    // 현재 위치 변경하기
    @Transactional
    public BaseRouteStartDto updateStartInfo(BaseRouteStartUpdateDto dto, User user) {
        BaseRoute baseRoute = baseRouteRepository.findByIdAndUser(dto.getId(), user)
                .orElseThrow(() -> new CustomException(ErrorCode.BASEROUTE_NOT_FOUND));
        baseRoute.updateStartCoordination(dto.getStartX(), dto.getStartY());
        return new BaseRouteStartDto(dto.getId(), dto.getStartX(), dto.getStartY(), baseRoute.getDayInfo());
    }

    // 역 ID와 역 이름 가져오기
    @Transactional(readOnly = false)
    public Optional<BaseRoute> getStationIdAndName(Long id) throws IOException {
        // baseRoute 객체 찾기
        Optional<BaseRoute> baseRoute = baseRouteRepository.findById(id);
        if (baseRoute.isEmpty()) {
            throw new CustomException(ErrorCode.BASEROUTE_NOT_FOUND);
        }
        // json 가져오기
        String jsonString = getJson(baseRoute.get().getStartX(), baseRoute.get().getStartY(), baseRoute.get().getEndX(),
                baseRoute.get().getEndY());
        // parsing
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            BaseRouteJsonDto jsonDto = mapper.readValue(jsonString, BaseRouteJsonDto.class);
            // 원하는 값을 추출
            String firstStation = jsonDto.getResult().getPath().get(0).getInfo().getFirstStartStation();
            String lastStation = jsonDto.getResult().getPath().get(0).getInfo().getLastEndStation();

            int startId = jsonDto.getResult().getPath().get(0).getSubPath().get(1).getStartID();

            int subPathLen = jsonDto.getResult().getPath().get(0).getSubPath().size();
            int endId = jsonDto.getResult().getPath().get(0).getSubPath().get(subPathLen - 2).getEndID();
            // 해당 디비 row에 저장
            baseRoute.get().saveIdAndNameInfo(startId, endId, firstStation, lastStation);
            return baseRoute;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = false)
    public String getFromAPI(int SID, int EID) throws IOException {
        //1. API 연결

        String urlInfo = "https://api.odsay.com/v1/api/subwayPath?lang=0&CID=1000&SID="+ SID +"&EID="+ EID +"&Sopt=2&apiKey=" + URLEncoder.encode(apiKey.getApiKey(), "UTF-8");

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

    @Transactional
    public BaseRoute findTransferData(Long id) throws IOException {
        //기존에 존재하는 baseRoute id로 해당 row 찾기
        BaseRoute baseRoute = baseRouteRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BASEROUTE_NOT_FOUND));
        //미리 저장된 출발역과 도착역 정보를 넣어 API 받기
        String string = getFromAPI(baseRoute.getSID(), baseRoute.getEID());
        //원하는 데이터 찾기
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(string); // jsonString은 JSON 문자열을 담고 있는 변수

            //각 배열에 접근
            JsonNode driveInfoArray = jsonNode.get("result").get("driveInfoSet").get("driveInfo");
            JsonNode stationSetArray = jsonNode.get("result").get("stationSet").get("stations");

            System.out.println("baseRouteService : "+ baseRoute.getExSID1());

            // "exSID" 값을 저장할 리스트 생성
            List<String> laneNameList = new ArrayList<>();  //몇호선(ex. 9호선-> 1호선)
            List<Integer> wayCodeList = new ArrayList<>();  //wayCode(방면코드. 1:상행 2:하행)
            List<String> wayNameList = new ArrayList<>();   //방면명
            List<String> exNameList = new ArrayList<>();    //환승역 이름
            List<Integer> exSIDList2 = new ArrayList<>();    //환승역의 두번째 stationId
            List<Integer> exSIDList1 = new ArrayList<>();   //환승역의 첫번쨰 stationId
            List<String> fastTrainDoorList = new ArrayList<>();    //최소환승
            List<Integer> exWalkTimeList = new ArrayList<>();//환승 역 사이의 소요시간

//            List<Integer> travelTimeList = new ArrayList<>();   //역<->역 이동 시간

            String globalEndName = String.valueOf(jsonNode.get("result").get("globalEndName").asText());


            // driveInfoArray 내의 데이터
            for (JsonNode driveInfo : driveInfoArray) {
                String laneName = driveInfo.get("laneName").asText();
                Integer wayCode = driveInfo.get("wayCode").asInt();
                String wayName = driveInfo.get("wayName").asText();

                laneNameList.add(laneName);
                wayCodeList.add(wayCode);
                wayNameList.add(wayName);
            }

            //환승이 반드시 하나 이상 있는 경우
            // "exChangeInfo" 배열을 반복하면서 "exSID" 값을 추출하여 리스트에 추가
            if (baseRoute.getExSID1() != null) {
                JsonNode exChangeInfoArray = jsonNode.get("result").get("exChangeInfoSet").get("exChangeInfo");

                for (JsonNode exChangeInfo : exChangeInfoArray) {
                    String exName = exChangeInfo.get("exName").asText();
                    Integer exSID = exChangeInfo.get("exSID").asInt();
                    Integer fastTrain = exChangeInfo.get("fastTrain").asInt();
                    Integer fastDoor = exChangeInfo.get("fastDoor").asInt();
                    Integer exWalkTime = exChangeInfo.get("exWalkTime").asInt();
                    exWalkTime = exWalkTime / 60;
                    if (exWalkTime % 60 != 0) {
                        exWalkTime += 1;
                    }

                    exNameList.add(exName);
                    exSIDList2.add(exSID);
                    fastTrainDoorList.add(fastTrain+"-"+fastDoor);
                    exWalkTimeList.add(exWalkTime);
                }
            }


            if (!exNameList.isEmpty()) {
                int cnt = 0;
                int prev = 0;   //첫번째 환승역: 0, 두번쨰 환승역: 두번째 환승역의 travelTime - (첫번째 환승역+첫번째 환승역의 exWalkTime)
                int travelTime = 0;
                int currentTravelTime = 0;
                for (JsonNode stations : stationSetArray) {
                    if (exNameList.contains(stations.get("endName").asText()) ||stations.get("endName").asText().equals(globalEndName)) {
                        if (exNameList.contains(stations.get("endName").asText())) {
                            exSIDList1.add(stations.get("endSID").asInt());
                        }
//                        if (cnt == 0) {
//                            travelTime = stations.get("travelTime").asInt();
//                            prev = travelTime;
////                            travelTimeList.add(travelTime);
//                            cnt += 1;
//                        } else {
//                            currentTravelTime = stations.get("travelTime").asInt();
//                            prev += exWalkTimeList.get(cnt-1);
////                            travelTime = currentTravelTime - prev + 1;  //exWalkTime 시 반올림하기 때문에 발생하는 1분만 추가
////                            travelTimeList.add(travelTime);
//                            cnt += 1;
//                        }
                    }
                }
            }

            String StringLaneName = objectMapper.writeValueAsString(laneNameList);

            String StringWayCode = objectMapper.writeValueAsString(wayCodeList);

            String StringWayName = objectMapper.writeValueAsString(wayNameList);

//            String StringTravelTime = objectMapper.writeValueAsString(travelTimeList);

            if (baseRoute.getExSID1() != null) {
                String StringExName = objectMapper.writeValueAsString(exNameList);

                String StringExSID1 = objectMapper.writeValueAsString(exSIDList1);

                String StringExSID2 = objectMapper.writeValueAsString(exSIDList2);

                String StringFastTrain = objectMapper.writeValueAsString(fastTrainDoorList);

                String StringExWalkTime = objectMapper.writeValueAsString(exWalkTimeList);

                baseRoute.update(StringLaneName, StringWayCode, StringWayName, StringExName, StringExSID1, StringExSID2, StringFastTrain, StringExWalkTime);
            } else {
                baseRoute.updateOnly(StringLaneName, StringWayCode, StringWayName);
            }

            baseRouteRepository.save(baseRoute);

        } catch (Exception e) {
            log.info(e.toString());
        }
        return baseRoute;
    }

    @Transactional
    public BaseRoute findByUser(User user) {
        BaseRoute baseRoute = baseRouteRepository.findByUser(user).orElse(null);
        return baseRoute;
    }

}