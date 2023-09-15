package com.seulseul.seulseul.controller;

import com.seulseul.seulseul.entity.ApiKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@RestController
public class HelloController {
//
//    private final ApiKey apiService;
//
//    public HelloController(ApiKey apiService) {
//        this.apiService = apiService;
//    }
//
//    @GetMapping("/hello")
//    public String getHello() throws IOException {
//        String urlInfo = "https://api.odsay.com/v1/api/searchPubTransPathT?SX=126.9027279&SY=37.5349277&EX=126.9145430&EY=37.5499421&apiKey=" + URLEncoder.encode(apiService.getApiKey(), "UTF-8");
//
//        // http 연결
//        URL url = new URL(urlInfo);
//        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Content-type", "application/json");
//
//        BufferedReader bufferedReader =
//                new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = bufferedReader.readLine()) != null) {
//            sb.append(line);
//        }
//        bufferedReader.close();
//        conn.disconnect();
//
//        // 결과 출력
//        System.out.println(sb.toString());
//        return sb.toString();
//    }

}
