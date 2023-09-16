package com.seulseul.seulseul.repository.baseRoute;

import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseRouteRepository extends JpaRepository<BaseRoute, Long> {
    Optional<BaseRoute> findByStartXAndStartY(double startX, double startY);
}