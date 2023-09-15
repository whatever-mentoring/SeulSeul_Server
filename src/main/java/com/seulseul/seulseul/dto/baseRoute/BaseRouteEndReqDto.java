package com.seulseul.seulseul.dto.baseRoute;

import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import lombok.*;

@ToString
@Getter //dto.get() 사용 가능
@Setter //dto.set() 사용 가능
@NoArgsConstructor  //파라미터가 없는 생성자(기본 생성자) 자동 생성
@AllArgsConstructor
public class BaseRouteEndReqDto {
    private Long id;
    private double endX;
    private double endY;

}