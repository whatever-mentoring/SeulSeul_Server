package com.seulseul.seulseul.entity.android;

import com.seulseul.seulseul.dto.android.RouteDetailDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RouteDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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

    public RouteDetail(RouteDetailDto routeDetailDto) {
        this.firstStation = routeDetailDto.getFirstStation();
        this.lastStation = routeDetailDto.getLastStation();
        this.laneName = routeDetailDto.getLaneName();
        this.wayName = routeDetailDto.getWayName();
        this.exName = routeDetailDto.getExName();
        this.fastTrainDoor = routeDetailDto.getFastTrainDoor();
        this.exWalkTime = routeDetailDto.getExWalkTime();
        this.travelTime = routeDetailDto.getTravelTime();
        this.timeList = routeDetailDto.getTimeList();
        this.totalTime = routeDetailDto.getTotalTime();
    }
}
