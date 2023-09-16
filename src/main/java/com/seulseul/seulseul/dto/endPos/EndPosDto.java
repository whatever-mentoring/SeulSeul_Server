package com.seulseul.seulseul.dto.endPos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.endPos.EndPos;
import lombok.*;

@ToString
@Getter //dto.get() 사용 가능
@Setter //dto.set() 사용 가능
@NoArgsConstructor  //파라미터가 없는 생성자(기본 생성자) 자동 생성
@AllArgsConstructor //모든 필드를 파라미터로 받는 생성자 자동 생성
public class EndPosDto {

    @JsonProperty("endX")
    private double endX;

    @JsonProperty("endX")
    private double endY;

    @JsonProperty("endNickName")
    private String endNickName;

    @JsonProperty("roadNameAddress")
    private String roadNameAddress;

//    public EndPos toEntity(EndPosDto dto) {
//        EndPos entity = new EndPos();
//        entity.setEndX(dto.getEndX());
//        entity.setEndY(dto.getEndY());
//        entity.setEndNickName(dto.getEndNickName());
//        entity.setRoadNameAddress(dto.getRoadNameAddress());
//        return entity;
//    }

}
