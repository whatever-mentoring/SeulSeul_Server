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
    private List<Integer> exSIDList;

    public void updateExSID(List<Integer> exSIDList) {
        this.exSIDList = exSIDList;
    }
//    @Column(name="wayCode")
//    private int wayCode;
//
//    @Column(name="wayName")
//    private String wayName;
//
//    @Column(name="exWalkTime")
//    private int exWalkTime;
//
//    @Column(name="exName")
//    private String exName;
//
//    @Column(name="travelTime")
//    private int travelTime;
//
//    @Column(name="globalTravelTime")
//    private int globalTravelTime;


    //entity -> dto 변환
    public BaseRouteDto toDto(BaseRoute entity) {
        BaseRouteDto dto = new BaseRouteDto();
        dto.setId(entity.getId());
        dto.setStartx(entity.getStartX());
        dto.setStarty(entity.getStartY());
        dto.setEndx(entity.getEndX());
        dto.setEndy(entity.getEndY());
        return dto;
    }

}
