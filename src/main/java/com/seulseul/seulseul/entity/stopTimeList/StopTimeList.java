package com.seulseul.seulseul.entity.stopTimeList;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.util.List;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="StopTimeList")
public class StopTimeList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer stationId;
    private Long baseRouteId;
    private List<String> time;

    public StopTimeList(List<String> timeList) {
        this.time = timeList;
    }


    public void update(Long id, Integer stationId,List<String> timeList) {
        this.baseRouteId = id;
        this.stationId = stationId;
        this.time = timeList;
    }

//    public void update(List<Integer> hourList, List<String> minuteList) {
//        this.SIDIdx = hourList;
//        this.SIDList = minuteList;
//    }

}
