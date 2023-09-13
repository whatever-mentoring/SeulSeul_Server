package com.seulseul.seulseul.dto.baseRoute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class BaseRouteDto {
    private int startStation;
    private int endStation;
    private String firstStation;
    private String lastStation;
    private double startX;
    private double startY;
    private String dayInfo;

    public BaseRouteDto(BaseRouteStartReqDto dto) {
        this.startX = dto.getStartX();
        this.startY = dto.getStartY();
        this.dayInfo = dto.getDayInfo();
    }
}