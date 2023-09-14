package com.seulseul.seulseul.service.transferInfo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seulseul.seulseul.dto.transferInfo.TransferInfoDto;
import com.seulseul.seulseul.dto.transferInfo.TransferInfoJsonDto;
import com.seulseul.seulseul.entity.ApiKey;
import com.seulseul.seulseul.entity.transferInfo.TransferInfo;
import com.seulseul.seulseul.repository.baseRoute.BaseRouteRepository;
import com.seulseul.seulseul.repository.transferInfo.TransferInfoRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class TransferInfoService {
    private ApiKey apiKey;
    private TransferInfoRepository transferInfoRepository;
    private BaseRouteRepository baseRouteRepository;

    public TransferInfoService(ApiKey apiKey, TransferInfoRepository transferInfoRepository, BaseRouteRepository baseRouteRepository) {
        this.apiKey = apiKey;
        this.transferInfoRepository = transferInfoRepository;
        this.baseRouteRepository = baseRouteRepository;
    }

    public String getUrl() throws IOException {
        String urlInfo = "https://api.odsay.com/v1/api/subwayPath?lang=0&CID={}&SID={}&EID={}&Sopt=2&apiKey="
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

    public TransferInfoDto getTransferInfo() throws IOException{
        String jsonString = getJson();
        // parsing
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            TransferInfoJsonDto jsonDto = mapper.readValue(jsonString, TransferInfoJsonDto.class);
            String exName = jsonDto.getResult().getExChangeInfoSet().getExChangeInfo().get(0).getExName();
            int exWalkTime = jsonDto.getResult().getExChangeInfoSet().getExChangeInfo().get(0).getExWalkTime();
            int exSID = jsonDto.getResult().getExChangeInfoSet().getExChangeInfo().get(0).getExSID();

            TransferInfoDto dto = new TransferInfoDto();
            dto.setExName(exName);
            dto.setExWalkTime(exWalkTime);
            dto.setExSID(exSID);
            transferInfoRepository.save(new TransferInfo(dto.getExSID(), dto.getExName(), dto.getExWalkTime()));
            return dto;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
