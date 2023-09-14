package com.seulseul.seulseul.dto.test;

import com.seulseul.seulseul.entity.test.Test;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class TestDto {
    private String userId;
    private String id;
    private String title;
    private String body;

    public TestDto(Test test) {
        this.userId = test.getUserId();
        this.id = test.getId();
        this.title = test.getTitle();
        this.body = test.getBody();
    }
}
