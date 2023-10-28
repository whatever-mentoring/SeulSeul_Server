package com.seulseul.seulseul.repository.stopTimeList;

import com.seulseul.seulseul.entity.stopTimeList.StopTimeList;
import jakarta.persistence.EntityManager;
import org.hibernate.type.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StopTimeListRepository extends JpaRepository<StopTimeList, Long> {
    List<StopTimeList> findByBaseRouteId(Long baseRouteId);
}
