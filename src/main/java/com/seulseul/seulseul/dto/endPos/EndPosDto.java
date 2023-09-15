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
    @JsonProperty("id")
    private Long id;

    @JsonProperty("endx")
    private double endx;

    @JsonProperty("endy")
    private double endy;

    @JsonProperty("endNickName")
    private String endNickName;

    @JsonProperty("roadNameAddress")
    private String roadNameAddress;

    public EndPos toEntity(EndPosDto dto) {
        EndPos entity = new EndPos();
        entity.setId(dto.getId());
        entity.setEndx(dto.getEndx());
        entity.setEndy(dto.getEndy());
        entity.setEndNickName(dto.getEndNickName());
        entity.setRoadNameAddress(dto.getRoadNameAddress());
        return entity;
    }
}
