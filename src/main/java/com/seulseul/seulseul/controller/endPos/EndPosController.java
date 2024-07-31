package com.seulseul.seulseul.controller.endPos;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.seulseul.seulseul.dto.android.RouteDetailDto;
import com.seulseul.seulseul.dto.android.RouteDetailWrapDto;
import com.seulseul.seulseul.dto.endPos.EndPosDto;
import com.seulseul.seulseul.dto.endPos.EndPosResDto;
import com.seulseul.seulseul.dto.response.ResponseData;
import com.seulseul.seulseul.entity.android.RouteDetail;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.endPos.EndPos;
import com.seulseul.seulseul.entity.user.User;
import com.seulseul.seulseul.service.android.RouteDetailService;
import com.seulseul.seulseul.service.baseRoute.BaseRouteService;
import com.seulseul.seulseul.service.endPos.EndPosService;
import com.seulseul.seulseul.service.firebase.FcmService;
import com.seulseul.seulseul.service.result.UpdateResultService;
import com.seulseul.seulseul.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EndPosController {
	private final EndPosService endPosService;
	private final UserService userService;
	private final BaseRouteService baseRouteService;
	private final UpdateResultService updateResultService;
	private final RouteDetailService routeDetailService;
	private final FcmService fcmService;

	//(1)endPos table에 저장: 사용자가 입력한 값에 기반 -> (2)baseRoute table에 저장
	@PostMapping("/v1/end")
	public ResponseEntity<?> createEndPos(@RequestBody EndPosDto form, @RequestHeader("Auth") UUID uuid) {
		User user = userService.findUserByUuid(uuid);
		EndPosResDto dto = endPosService.saveEndPos(form, user);
		ResponseData responseData = new ResponseData(200, dto);
		return ResponseEntity.ok(responseData);
	}

	// 유저의 모든 목적지 List로 보여주기
	@GetMapping("/v1/end")
	public ResponseEntity<ResponseData> getAllEndPos(@RequestHeader("Auth") UUID uuid) {
		User user = userService.findUserByUuid(uuid);
		List<EndPos> endPosList = endPosService.findAllEndPos(user);
		ResponseData responseData = new ResponseData(200, endPosList);
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	// 선택한 목적지 보여주기
	@GetMapping("/v1/end/{id}")
	public ResponseEntity<ResponseData> getEndPos(@RequestHeader("Auth") UUID uuid, @PathVariable("id") Long id) throws
		IOException,
		ParseException {
		User user = userService.findUserByUuid(uuid);
		BaseRoute baseRoute = baseRouteService.findBaseRouteByUser(user);
		EndPosResDto dto = endPosService.findEndPos(id, user);

		if (baseRoute.getAlarm() != null && baseRoute.getAlarm().isAlarmEnabled() == true) {
			//<추가>baseRoute 경로 설정
			RouteDetailDto routeDetailDto = new RouteDetailDto();
			routeDetailDto = updateResultService.findUpdatedResult(baseRoute.getId());
			RouteDetailWrapDto wrapDto = new RouteDetailWrapDto();
			// RouteDetail DB에 저장
			RouteDetail routeDetail = routeDetailService.saveRouteDetail(routeDetailDto, baseRoute);
			// 환승이 있으면
			if (routeDetailDto.getExName() != null) {
				wrapDto.setBodyExList(routeDetailDto);
			}
			// 환승이 없으면
			else {
				wrapDto.setBodyList(routeDetailDto);
			}
			wrapDto.setTimeList(routeDetailDto);
			fcmService.schedule(baseRoute);
		}
		ResponseData responseData = new ResponseData(200, dto);
		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
}