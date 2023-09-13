package com.seulseul.seulseul.service.baseRoute;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteJsonDto;
import com.seulseul.seulseul.entity.ApiKey;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class BaseRouteService {
    private ApiKey apiKey;
    private BaseRouteRepository baseRouteRepository;

    public BaseRouteService(ApiKey apiKey, BaseRouteRepository baseRouteRepository) {
        this.apiKey = apiKey;
        this.baseRouteRepository = baseRouteRepository;
    }

    public String getUrl() throws IOException{
        String urlInfo = "https://api.odsay.com/v1/api/searchPubTransPathT?lang=0&SX={from android}&SY={from android}&EX={from android}&EY={from android}&SearchPathType=1&apiKey="
                + URLEncoder.encode(apiKey.getApiKey(), "UTF-8");
        return urlInfo;
    }

    public String getJson() throws IOException {
        // http 연결
        String urlInfo = getUrl();
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

    public BaseRouteDto getStationID() throws IOException {
        // json 받기
        String jsonString = getJson();
        // parsing
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            BaseRouteJsonDto jsonDto = mapper.readValue(jsonString, BaseRouteJsonDto.class);
            // 원하는 값을 추출
            String firstStation = jsonDto.getResult().getPath().get(0).getInfo().getFirstStartStation();
            String lastStation = jsonDto.getResult().getPath().get(0).getInfo().getLastEndStation();

            int startStation = jsonDto.getResult().getPath().get(0).getSubPath().get(1).getStartID();

            int subPathLen = jsonDto.getResult().getPath().get(0).getSubPath().size();
            int endStation = jsonDto.getResult().getPath().get(0).getSubPath().get(subPathLen - 2).getEndID();

            // 여기서 인스턴스화 괜찮..?
            BaseRouteDto dto = new BaseRouteDto();
            // setter말고 다르게 저장하자 -> setter 괜찮다. 단순 데이터 전달이라서. 근데 너무 코드 가독성이 떨어진다..
            dto.setFirstStation(firstStation);
            dto.setLastStation(lastStation);
            dto.setStartStation(startStation);
            dto.setEndStation(endStation);
            baseRouteRepository.save(new BaseRoute(dto.getFirstStation(), dto.getLastStation(),dto.getStartStation(), dto.getEndStation()));
            return dto;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}