package com.seulseul.seulseul.entity.baseRoute;

import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
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

    @Column(name="transfer_station")
    private List<Integer> exSID;

    @Column(name="laneName")
    private List<String> laneName;

    @Column(name="wayCode")
    private List<Integer> wayCode;

    @Column(name="wayName")
    private List<String> wayName;

    @Column(name="fastDoor")
    private List<Integer> fastDoor;

    @Column(name="exWalkTime")
    private List<Integer> exWalkTime;

    @Column(name="exName")
    private List<String> exName;

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

    public void update(List<String> laneNameList, List<Integer> wayCodeList, List<String> wayNameList, List<String> exNameList, List<Integer> exSIDList, List<Integer> fastDoorList, List<Integer> exWalkTimeList) {
        this.laneName = laneNameList;

        this.laneName = laneNameList;
        this.wayCode = wayCodeList;
        this.wayName = wayNameList;
        this.exName = exNameList;
        this.exSID = exSIDList;
        this.fastDoor = fastDoorList;
        this.exWalkTime = exWalkTimeList;
    }
    //entity -> dto 변환
    public BaseRouteDto toDto(BaseRoute entity) {
        BaseRouteDto dto = new BaseRouteDto();
        dto.setStartx(entity.getStartX());
        dto.setStarty(entity.getStartY());
        dto.setEndx(entity.getEndX());
        dto.setEndy(entity.getEndY());
        return dto;
    }
}
