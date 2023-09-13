package com.seulseul.seulseul.dto.baseRoute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BaseRouteStartReqDto {
    private double startX;
    private double startY;
    private String dayInfo;
}
