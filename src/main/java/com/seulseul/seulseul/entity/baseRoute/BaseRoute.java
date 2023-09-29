package com.seulseul.seulseul.entity.baseRoute;

import com.seulseul.seulseul.entity.alarm.Alarm;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.entity.android.RouteDetail;
import com.seulseul.seulseul.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="BaseRoute")
public class BaseRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double startX;

    private double startY;

    private double endX;

    private double endY;

    @Column(name="SID")
    private int SID;

    @Column(name="EID")
    private int EID;


    // 출발역 이름
    private String firstStation;

    // 도착역 이름
    private String lastStation;

    // 요일
    private String dayInfo;
    @Column(name="exSID")
    private String exSID1;

    @Column(name="exSID2")
    private String exSID2;

    @Column(name="laneName")
    private String laneName;

    @Column(name="wayCode")
    private String wayCode;

    @Column(name="wayName")
    private String wayName;

    @Column(name="fastTrainDoor")
    private String fastTrainDoor;

    @Column(name="exWalkTime")
    private String exWalkTime;

    @Column(name="exName")
    private String exName;

    @Column(name="travelTime")
    private String travelTime;

    @OneToOne
    private User user;

    @OneToOne
    @JoinColumn(name = "alarm_id")
    private Alarm alarm;

    @OneToOne
    @JoinColumn(name = "route_detail_id")
    private RouteDetail routeDetail;

    public BaseRoute(BaseRouteDto baseRouteDto) {
        this.startX = baseRouteDto.getStartX();
        this.startY = baseRouteDto.getStartY();
        this.endX = baseRouteDto.getEndX();
        this.endY = baseRouteDto.getEndY();
        this.user = baseRouteDto.getUser();
    }

    public void saveStartInfo(double startX,double startY, String dayInfo) {
        this.startX = startX;
        this.startY = startY;
        this.dayInfo = dayInfo;
    }

    public void saveIdAndNameInfo(int SID, int EID, String firstStation, String lastStation) {
        this.SID = SID;
        this.EID = EID;
        this.firstStation = firstStation;
        this.lastStation = lastStation;
    }

    public void updateStartCoordination(double startX, double startY) {
        this.startX = startX;
        this.startY = startY;

    }

//     대중교통 길찾기 API
    public void update(String laneNameList, String wayCodeList, String wayNameList, String exNameList, String exSIDList1, String exSIDList2, String fastTrainDoorList, String exWalkTimeList, String travelTime) {
        this.laneName = laneNameList;

        this.laneName = laneNameList;
        this.wayCode = wayCodeList;
        this.wayName = wayNameList;
        this.exName = exNameList;
        this.exSID1 = exSIDList1;
        this.exSID2 = exSIDList2;
        this.fastTrainDoor = fastTrainDoorList;
        this.exWalkTime = exWalkTimeList;
        this.travelTime = travelTime;
    }

    public void updateOnly(String laneNameList, String wayCodeList, String wayNameList, String travelTime) {
        this.laneName = laneNameList;
        this.wayCode = wayCodeList;
        this.wayName = wayNameList;
        this.travelTime = travelTime;
    }

    public void saveAlarmInfo(Alarm alarm) {
        this.alarm = alarm;
    }

    public void saveRouteDetail(RouteDetail routeDetail) {
        this.routeDetail = routeDetail;
    }

    public void updateEndCoordination(double endX, double endY) {
        this.endX = endX;
        this.endY = endY;
    }

    public void init() {
        this.exName = null;
        this.exSID1 = null;
        this.exSID2 = null;
        this.fastTrainDoor = null;
        this.exWalkTime = null;
    }

    //entity -> dto 변환
    public BaseRouteDto toDto(BaseRoute entity) {
        BaseRouteDto dto = new BaseRouteDto();
        dto.setStartX(entity.getStartX());
        dto.setStartY(entity.getStartY());
        dto.setEndX(entity.getEndX());
        dto.setEndY(entity.getEndY());
        return dto;
    }

}
