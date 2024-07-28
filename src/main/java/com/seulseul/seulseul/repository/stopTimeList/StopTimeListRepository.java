package com.seulseul.seulseul.repository.stopTimeList;

import com.seulseul.seulseul.entity.stopTimeList.StopTimeList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StopTimeListRepository extends JpaRepository<StopTimeList, Long> {
    List<StopTimeList> findByBaseRouteId(Long baseRouteId);
}
