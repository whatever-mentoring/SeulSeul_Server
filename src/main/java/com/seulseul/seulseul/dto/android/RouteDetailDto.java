package com.seulseul.seulseul.dto.android;

import lombok.*;

import java.util.List;

@ToString
@Getter //dto.get() 사용 가능
@Setter //dto.set() 사용 가능
@NoArgsConstructor  //파라미터가 없는 생성자(기본 생성자) 자동 생성
@AllArgsConstructor //모든 필드를 파라미터로 받는 생성자 자동 생성
public class RouteDetailDto {

    private String firstStation;    //출발역 이름
    private String lastStation;     //도착역 이름

    private String[] laneName;    //지하철 호선
    private String[] wayName;   //방면(ex.중앙보훈병원 방면)
    private String[] exName;    //환승역 이름
    private  String[] fastTrainDoor; //빠른 환승
    private String[] exWalkTime;   //환승역에서 환승하는데 소요되는 시간
    private String[] travelTime;



    //저장
    private String timeList;
    private String totalTime;


    //firstStation, lastStation, exName, exWalkTime, fastTrainDoor, laneName, wayName

    public void updateFromBaseRoute(String firstStation, String lastStation, String[] exName, String[] exWalkTime, String[] fastTrainDoor, String[] laneName, String[] wayName, String[] travelTime) {

        this.firstStation = firstStation;
        this.lastStation = lastStation;
        this.exName = exName;
        this.exWalkTime = exWalkTime;
        this.fastTrainDoor = fastTrainDoor;
        this.laneName = laneName;
        this.wayName = wayName;
        this.travelTime = travelTime;
    }

    public void updateFromBaseRouteOnly(String firstStation, String lastStation, String[] laneName, String[] wayName, String[] travelTime) {
        this.firstStation = firstStation;
        this.lastStation = lastStation;
        this.laneName = laneName;
        this.wayName = wayName;
        this.travelTime = travelTime;

    }

    public void updateTimeList(String timeList, String totalTime) {
        this.timeList = timeList;
        this.totalTime = totalTime;
    }
}
