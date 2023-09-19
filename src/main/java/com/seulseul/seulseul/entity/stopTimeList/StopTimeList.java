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

//    private Integer SID;
//
//    private Integer EID;
//
//    private List<Integer> ExSID;
//
//    private List<Integer> SIDIdx;
//
//    private List<String> SIDList;
//
//    private List<Integer> EIDIdx;
//    private List<String> EIDList;
//    private List<Integer> ExSIDIdx;
//    private List<String> ExSIDList;

    private List<String> time;

    public StopTimeList(List<String> timeList) {
        this.time = timeList;
    }

//    public void update(List<Integer> hourList, List<String> minuteList) {
//        this.SIDIdx = hourList;
//        this.SIDList = minuteList;
//    }

}
