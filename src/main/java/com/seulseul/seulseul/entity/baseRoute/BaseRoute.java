package com.seulseul.seulseul.entity.baseRoute;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BaseRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double endX;

    private double endY;

    private int startStation;

    private int endStation;

    // 출발역 이름
    private String firstStation;

    // 도착역 이름
    private String lastStation;

    public BaseRoute(String firstStation, String lastStation, int startStation, int endStation) {
        this.firstStation = firstStation;
        this.lastStation= lastStation;
        this.startStation = startStation;
        this.endStation = endStation;
    }
}
