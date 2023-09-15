package com.seulseul.seulseul.controller.baseRoute;

import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.dto.endPos.EndPosDto;
import com.seulseul.seulseul.entity.ApiKey;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import com.seulseul.seulseul.service.endPos.EndPosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Controller
@RestController
@RequiredArgsConstructor    //service 선언한 후 초기화 단계 필요한데, 초기화하지 않아도 되도록 해줌
public class BaseRouteController {

    private final BaseRouteService baseRouteService;
    private final ApiKey apiService;

//    public HelloController(ApiKey apiService) {
//        this.apiService = apiService;
//    }

    @GetMapping("/hello")
    public String getHello() throws IOException {
        String urlInfo = "https://api.odsay.com/v1/api/searchPubTransPathT?SX=126.9027279&SY=37.5349277&EX=126.9145430&EY=37.5499421&apiKey=" + URLEncoder.encode(apiService.getApiKey(), "UTF-8");

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

        // 결과 출력
        System.out.println(sb.toString());
        return "hello";
    }

    @GetMapping("/getTransferStation")
    public void getTransferStation() throws IOException{
        //1. API 연결
        int SID = 914;  //출발역 stationId
        int EID = 134;  //도착역 stationId

        String urlInfo = "https://api.odsay.com/v1/api/subwayPath?lang=0&CID=1000&SID="+ SID +"&EID="+ EID +"&apiKey=" + URLEncoder.encode(apiService.getApiKey(), "UTF-8");

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

        // 결과 출력
        System.out.println(sb.toString());


        //2. API에서 필요한 데이터 추출 + DB에 저장
        // 필요한 데이터(exSID(환승역 id), wayCode,  wayName[방면 명], exSID, exWalkTime, exName[환승역 명], travleTime) repository에 저장
//        BaseRouteDto baseRouteDto = baseRouteService.getTransferStation(SID, EID, sb.toString());

//        return ;
    }

}
