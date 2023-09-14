package com.seulseul.seulseul.entity.baseRoute;

import com.seulseul.seulseul.entity.transferInfo.TransferInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Entity
public class BaseRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double startX;

    private double startY;

    private double endX;

    private double endY;

    private int startStation;

    private int endStation;

    // 출발역 이름
    private String firstStation;

    // 도착역 이름
    private String lastStation;

    // 요일
    private String dayInfo;

    public BaseRoute(String firstStation, String lastStation, int startStation, int endStation, double startX, double startY) {
        this.firstStation = firstStation;
        this.lastStation= lastStation;
        this.startStation = startStation;
        this.endStation = endStation;
        this.startX = startX;
        this.startY = startY;
    }

    public BaseRoute(double startX, double startY, String dayInfo) {
        this.startX = startX;
        this.startY = startY;
        this.dayInfo = dayInfo;
    }
}
