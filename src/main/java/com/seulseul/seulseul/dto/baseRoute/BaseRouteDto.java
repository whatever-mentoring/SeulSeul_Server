package com.seulseul.seulseul.dto.baseRoute;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import lombok.*;

@ToString
@Getter //dto.get() 사용 가능
@Setter //dto.set() 사용 가능
@NoArgsConstructor  //파라미터가 없는 생성자(기본 생성자) 자동 생성
@AllArgsConstructor //모든 필드를 파라미터로 받는 생성자 자동 생성
public class BaseRouteDto {

    @JsonProperty("startx")
    private double startx;

    @JsonProperty("starty")
    private double starty;

    @JsonProperty("endx")
    private double endx;

    @JsonProperty("endy")
    private double endy;

    @JsonProperty("startStationId")
    private int startStationId;

    @JsonProperty("endStationId")
    private int endStationId;

    //dto -> entity 변환
//    public BaseRoute toEntity(BaseRouteDto dto) {
//        BaseRoute entity = new BaseRoute();
//        entity.setId(dto.getId());
//        entity.setStartx(dto.getStartx());
//        entity.setStarty(dto.getStarty());
//        entity.setEndx(dto.getEndx());
//        entity.setEndy(dto.getEndy());
//        return entity;
//    }

}
