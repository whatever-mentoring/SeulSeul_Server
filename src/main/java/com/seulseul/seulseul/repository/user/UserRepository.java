package com.seulseul.seulseul.repository.user;

import com.seulseul.seulseul.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUuid(UUID uuid);
}
