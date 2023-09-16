package com.seulseul.seulseul.service.baseRoute;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seulseul.seulseul.dto.baseRoute.*;
import com.seulseul.seulseul.dto.endPos.EndPosDto;
import com.seulseul.seulseul.entity.ApiKey;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.endPos.EndPos;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import com.seulseul.seulseul.repository.endPos.EndPosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @org.springframework.transaction.annotation.Transactional
    public BaseRouteStartDto saveStartInfo(BaseRouteStartReqDto reqDto) {
        Optional<BaseRoute> baseRoute = baseRouteRepository.findById(reqDto.getId());
        baseRoute.get().saveStartInfo(reqDto.getStartX(), reqDto.getStartY(), reqDto.getDayInfo());
        return new BaseRouteStartDto(baseRoute.get().getId(), reqDto.getStartX(), reqDto.getStartY(), reqDto.getDayInfo());
    }

    // 현재 위치 변경하기
    @Transactional(readOnly = false)
    public BaseRouteStartDto updateStartInfo(BaseRouteStartUpdateDto dto) {
        Optional<BaseRoute> baseRoute = baseRouteRepository.findById(dto.getId());
        baseRoute.get().updateStartCoordination(dto.getStartX(), dto.getStartY());
        return new BaseRouteStartDto(dto.getId(), dto.getStartX(), dto.getStartY(), baseRoute.get().getDayInfo());
    }

    // 역 ID와 역 이름 가져오기
    @Transactional(readOnly = false)
    public Optional<BaseRoute> getStationIdAndName(Long id) throws IOException {
        // baseRoute 객체 찾기
        Optional<BaseRoute> baseRoute = baseRouteRepository.findById(id);
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
            // 객체 찾고
            // 여기서 인스턴스화 괜찮..?
            BaseRouteDto dto = new BaseRouteDto();
            // setter말고 다르게 저장하자 -> setter 괜찮다. 단순 데이터 전달이라서. 근데 너무 코드 가독성이 떨어진다..
//            dto.setFirstStation(firstStation);
//            dto.setLastStation(lastStation);
//            dto.setStartId(startId);
//            dto.setEndId(endId);
            /*baseRouteRepository.save(new BaseRoute(dto.getFirstStation(), dto.getLastStation(),dto.getStartId(), dto.getEndId()
                                                    , dto.getStartX(), dto.getStartY()));*/
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