package com.seulseul.seulseul.dto.android;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteTransferDto;
import lombok.*;

import java.util.Date;
import java.util.List;

@ToString
@Getter //dto.get() 사용 가능
@Setter //dto.set() 사용 가능
@NoArgsConstructor  //파라미터가 없는 생성자(기본 생성자) 자동 생성
@AllArgsConstructor //모든 필드를 파라미터로 받는 생성자 자동 생성
public class RouteDetailDto {

    private String firstStation;    //출발역 이름
    private String lastStation;     //도착역 이름

    private List<String> laneName;    //지하철 호선
    private List<String> wayName;   //방면(ex.중앙보훈병원 방면)
    private List<String> exName;    //환승역 이름
    private  List<String> fastTrainDoor; //빠른 환승
    private List<Integer> exWalkTime;   //환승역에서 환승하는데 소요되는 시간
    private List<Integer> travelTime;   //누적 시간: 환승을 2번 이상 하는 경우 역<->역 시간은 exWalkTime을 뺀 시간


    //아직 구현 X
    private List<String> timeList;
    private Date minTime;   //출발역에서의 출발 시간
    private Date departTime;    //지하철 역에서 지하철이 출발하는 시간


    //firstStation, lastStation, exName, exWalkTime, fastTrainDoor, laneName, wayName
    public void updateFromBaseRoute(String firstStation, String lastStation, List<String> exName, List<Integer> exWalkTime, List<String> fastTrainDoor, List<String> laneName, List<String> wayName) {
        this.firstStation = firstStation;
        this.lastStation = lastStation;
        this.exName = exName;
        this.exWalkTime = exWalkTime;
        this.fastTrainDoor = fastTrainDoor;
        this.laneName = laneName;
        this.wayName = wayName;
    }

    public void updateTimeList(List<String> timeList) {
        this.timeList = timeList;
    }
}
