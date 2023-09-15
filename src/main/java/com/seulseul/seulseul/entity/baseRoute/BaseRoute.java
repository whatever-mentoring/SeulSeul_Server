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

    @Column(name = "start_x")
    private double startX;

    @Column(name="start_y")
    private double startY;

    @Column(name = "end_x")
    private double endX;

    @Column(name="end_y")
    private double endY;

    @Column(name="SID")
    private int SID;

    @Column(name="EID")
    private int EID;

    @Column(name="start_station")
    private int startStationId;

    @Column(name="end_station")
    private int endStationId;

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
