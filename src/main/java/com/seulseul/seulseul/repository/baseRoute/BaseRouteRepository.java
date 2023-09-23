package com.seulseul.seulseul.repository.baseRoute;

import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

public interface BaseRouteRepository extends JpaRepository<BaseRoute, Long> {
    Optional<BaseRoute> findByStartXAndStartY(double startX, double startY);

    Optional<BaseRoute> findByIdAndUser(Long id, User user);
  
    Optional<BaseRoute> findByUser(User user);
}