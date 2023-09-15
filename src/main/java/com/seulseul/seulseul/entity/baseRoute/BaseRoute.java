package com.seulseul.seulseul.entity.baseRoute;

import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class BaseRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double startX;

    private double startY;

    private double endX;

    private double endY;

    // 출발역 ID
    private int startStationId;

    // 도착역 ID
    private int endStationId;

    // 출발역 이름
    private String firstStation;

    // 도착역 이름
    private String lastStation;

    // 요일
    private String dayInfo;

    public BaseRoute(BaseRouteDto baseRouteDto) {
        this.id = baseRouteDto.getId();
        this.startX = baseRouteDto.getStartX();
        this.startY = baseRouteDto.getStartY();
        this.endX = baseRouteDto.getEndX();
        this.endY = baseRouteDto.getEndY();
    }

    public void saveStartInfo(double startX,double startY, String dayInfo) {
        this.startX = startX;
        this.startY = startY;
        this.dayInfo = dayInfo;
    }

    public void saveIdAndNameInfo(int startStationId, int endStationId, String firstStation, String lastStation) {
        this.startStationId = startStationId;
        this.endStationId = endStationId;
        this.firstStation = firstStation;
        this.lastStation = lastStation;
    }

    public void updateStartCoordination(double startX, double startY) {
        this.startX = startX;
        this.startY = startY;

    }

    //entity -> dto 변환
    public BaseRouteDto toDto(BaseRoute entity) {
        BaseRouteDto dto = new BaseRouteDto();
        dto.setId(entity.getId());
        dto.setStartY(entity.getStartX());
        dto.setStartY(entity.getStartY());
        dto.setEndX(entity.getEndX());
        dto.setEndY(entity.getEndY());
        return dto;
    }
}