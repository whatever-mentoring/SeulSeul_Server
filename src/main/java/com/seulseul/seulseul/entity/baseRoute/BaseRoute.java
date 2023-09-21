package com.seulseul.seulseul.entity.baseRoute;

import com.seulseul.seulseul.entity.alarm.Alarm;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
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
    private List<Integer> exSID1;

    @Column(name="exSID2")
    private List<Integer> exSID2;

    @Column(name="laneName")
    private List<String> laneName;

    @Column(name="wayCode")
    private List<Integer> wayCode;

    @Column(name="wayName")
    private List<String> wayName;

    @Column(name="fastTrainDoor")
    private List<String> fastTrainDoor;

    @Column(name="exWalkTime")
    private List<Integer> exWalkTime;

    @Column(name="exName")
    private List<String> exName;

    @Column(name="travelTime")
    private List<Integer> travelTime;

    @OneToOne
    private User user;

    @OneToOne
    @JoinColumn(name = "alarm_id")
    private Alarm alarm;

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

    // 대중교통 길찾기 API
    public void update(List<String> laneNameList, List<Integer> wayCodeList, List<String> wayNameList, List<String> exNameList, List<Integer> exSIDList1, List<Integer> exSIDList2, List<String> fastTrainDoorList, List<Integer> exWalkTimeList, List<Integer> travelTime) {
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

    public void saveAlarmInfo(Alarm alarm) {
        this.alarm = alarm;
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
