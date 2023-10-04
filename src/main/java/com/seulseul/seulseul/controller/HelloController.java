package com.seulseul.seulseul.controller;

import com.seulseul.seulseul.dto.test.TestDto;
import com.seulseul.seulseul.dto.test.TestUpdateDto;
import com.seulseul.seulseul.entity.ApiKey;
import com.seulseul.seulseul.entity.test.Test;
import com.seulseul.seulseul.service.test.TestService;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@RestController
public class HelloController {
}
