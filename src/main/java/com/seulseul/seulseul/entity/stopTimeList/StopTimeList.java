package com.seulseul.seulseul.entity.stopTimeList;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;
import org.hibernate.type.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
    private String time;

    public void update(Long id, Integer stationId,String timeList) {
        this.baseRouteId = id;
        this.stationId = stationId;
        this.time = timeList;
    }

}
