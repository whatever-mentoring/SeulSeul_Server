package com.seulseul.seulseul.entity.test;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Test {
    @Id
    private Long userId;
    private Long id;
    private String title;
    private String body;

    public Test(Long userId, Long id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }
}
