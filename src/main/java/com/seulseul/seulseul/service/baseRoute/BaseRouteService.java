package com.seulseul.seulseul.service.baseRoute;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seulseul.seulseul.dto.baseRoute.*;
import com.seulseul.seulseul.entity.ApiKey;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BaseRouteService {
    private ApiKey apiKey;
    private BaseRouteRepository baseRouteRepository;

    public BaseRouteService(ApiKey apiKey, BaseRouteRepository baseRouteRepository) {
        this.apiKey = apiKey;
        this.baseRouteRepository = baseRouteRepository;
    }

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
    @Transactional(readOnly = false)
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
}