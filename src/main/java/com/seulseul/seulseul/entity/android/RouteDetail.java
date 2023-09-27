package com.seulseul.seulseul.entity.android;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seulseul.seulseul.dto.android.RouteDetailDto;
import com.seulseul.seulseul.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RouteDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private String firstStation;    //출발역 이름
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private String lastStation;     //도착역 이름
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private String laneName;    //지하철 호선
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private String wayName;   //방면(ex.중앙보훈병원 방면)
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private String exName;    //환승역 이름
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private String fastTrainDoor; //빠른 환승
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private String exWalkTime;   //환승역에서 환승하는데 소요되는 시간

    private String travelTime;    //역<->역 이동시간
    private String timeList;
    private String totalTime;

    @JsonIgnore
    @ManyToOne
    private User user;

    public RouteDetail(RouteDetailDto dto, User user) {
        this.firstStation = dto.getFirstStation();
        this.lastStation = dto.getLastStation();
        this.laneName = dto.getLaneName();
        this.wayName = dto.getWayName();
        this.exName = dto.getExName();
        this.fastTrainDoor = dto.getFastTrainDoor();
        this.exWalkTime = dto.getExWalkTime();
        this.travelTime = dto.getTravelTime();
        this.timeList = dto.getTimeList();
        this.totalTime = dto.getTotalTime();
        this.user = user;
    }
}