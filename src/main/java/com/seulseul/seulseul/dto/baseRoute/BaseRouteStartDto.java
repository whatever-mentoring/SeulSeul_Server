package com.seulseul.seulseul.dto.baseRoute;

import lombok.Getter;

@Getter
public class BaseRouteStartDto {
    private Long id;
    private double startX;
    private double startY;
    private String dayInfo;

    public BaseRouteStartDto(Long id, double startX, double startY, String dayInfo) {
        this.id = id;
        this.startX = startX;
        this.startY = startY;
        this.dayInfo = dayInfo;
    }
}
