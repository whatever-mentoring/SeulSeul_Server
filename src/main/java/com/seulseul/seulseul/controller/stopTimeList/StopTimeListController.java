package com.seulseul.seulseul.controller.stopTimeList;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seulseul.seulseul.dto.response.ResponseData;
import com.seulseul.seulseul.entity.stopTimeList.StopTimeList;
import com.seulseul.seulseul.service.stopTimeList.StopTimeListService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor    //service 선언한 후 초기화 단계 필요한데, 초기화하지 않아도 되도록 해줌
public class StopTimeListController {
	private final StopTimeListService stopTimeListService;

	@PostMapping("/stopTimeList/{id}")
	public ResponseEntity<ResponseData> getStopTimeList(@PathVariable Long id) throws IOException {
		StopTimeList stopTimeList = stopTimeListService.findStopTimeListData(id);
		ResponseData responseData = new ResponseData(200, stopTimeList);
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
}
