package com.seulseul.seulseul.dto.firebase;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RequestDto {
    private String title;
    private String body;
    private String targetToken;
}