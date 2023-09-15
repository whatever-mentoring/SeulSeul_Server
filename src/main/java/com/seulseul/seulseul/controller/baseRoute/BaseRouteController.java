package com.seulseul.seulseul.controller.baseRoute;

import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.entity.ApiKey;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
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
//@RequiredArgsConstructor    //service 선언한 후 초기화 단계 필요한데, 초기화하지 않아도 되도록 해줌
public class BaseRouteController {


}
