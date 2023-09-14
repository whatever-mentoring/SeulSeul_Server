package com.seulseul.seulseul.entity.test;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Test {
    @Id
    private String userId;
    private String  id;
    private String title;
    private String body;

    public Test(String userId, String id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }
}
