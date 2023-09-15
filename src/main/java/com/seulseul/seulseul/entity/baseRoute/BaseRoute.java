package com.seulseul.seulseul.entity.baseRoute;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
;

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
    private int startId;

    // 도착역 ID
    private int endId;

    // 출발역 이름
    private String firstStation;

    // 도착역 이름
    private String lastStation;

    // 요일
    private String dayInfo;

    public BaseRoute(String firstStation, String lastStation, int startId, int endId, double startX, double startY) {
        this.firstStation = firstStation;
        this.lastStation= lastStation;
        this.startId = startId;
        this.endId = endId;
        this.startX = startX;
        this.startY = startY;
    }

    public BaseRoute(double startX, double startY, String dayInfo) {
        this.startX = startX;
        this.startY = startY;
        this.dayInfo = dayInfo;
    }

    public void saveStartInfo(double startX,double startY, String dayInfo) {
        this.startX = startX;
        this.startY = startY;
        this.dayInfo = dayInfo;
    }

    public void saveInfo(double startX, double startY, double endX, double endY, int startId, int endId, String firstStation, String lastStation
                            , String dayInfo) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.startId = startId;
        this.endId = endId;
        this.firstStation = firstStation;
        this.lastStation = lastStation;
        this.dayInfo = dayInfo;
    }

    public void updateStartCoordination(double startX, double startY) {
        this.startX = startX;
        this.startY = startY;
    }
}
