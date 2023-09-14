package com.seulseul.seulseul.repository.test;

import com.seulseul.seulseul.entity.test.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}

