package com.seulseul.seulseul.dto.baseRoute;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seulseul.seulseul.entity.alarm.Alarm;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.user.User;
import lombok.*;

@ToString
@Getter //dto.get() 사용 가능
@Setter //dto.set() 사용 가능
@NoArgsConstructor  //파라미터가 없는 생성자(기본 생성자) 자동 생성
@AllArgsConstructor //모든 필드를 파라미터로 받는 생성자 자동 생성
public class BaseRouteDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("startx")
    private double startX;

    @JsonProperty("starty")
    private double startY;

    @JsonProperty("endx")
    private double endX;

    @JsonProperty("endy")
    private double endY;

    @JsonProperty("startStationId")
    private int startStationId;

    @JsonProperty("endStationId")
    private int endStationId;

    private User user;

    private Alarm alarm;
    //dto -> entity 변환
//    public BaseRoute toEntity(BaseRouteDto dto) {
//        BaseRoute entity = new BaseRoute();
//        entity.setId(dto.getId());
//        entity.setStartX(dto.getStartX());
//        entity.setStartY(dto.getStartY());
//        entity.setEndX(dto.getEndX());
//        entity.setEndY(dto.getEndY());
//        return entity;
//    }
}
