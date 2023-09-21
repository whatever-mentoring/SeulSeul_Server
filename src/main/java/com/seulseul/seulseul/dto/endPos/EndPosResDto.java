package com.seulseul.seulseul.dto.endPos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EndPosResDto {
    private Long id;
    private Long base_route_id;
    private double endX;
    private double endY;
    private String endNickName;
    private String roadNameAddress;
}
