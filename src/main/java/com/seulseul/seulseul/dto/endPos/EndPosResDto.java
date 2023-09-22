package com.seulseul.seulseul.dto.endPos;

import com.seulseul.seulseul.entity.endPos.EndPos;
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
    private String jibunAddress;

    public EndPosResDto(EndPos endPos, Long base_route_id) {
        this.id = endPos.getId();
        this.base_route_id = base_route_id;
        this.endX = endPos.getEndX();
        this.endY = endPos.getEndY();
        this.endNickName = endPos.getEndNickName();
        this.roadNameAddress = endPos.getRoadNameAddress();
        this.jibunAddress = endPos.getJibunAddress();
    }
}
