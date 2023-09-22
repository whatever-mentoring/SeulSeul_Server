package com.seulseul.seulseul.entity.endPos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.dto.endPos.EndPosDto;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import com.seulseul.seulseul.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="EndPos")
public class EndPos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "end_x") //경도
    private double endX;

    @Column(name="end_y")   //위도
    private double endY;

    @Column(name = "endNickName")   //endNickName:안드로이드 => Home: 본가, LivingAlone: 자취방, Dormitory: 기숙사, ??:기타(사용자지정)
    private String endNickName;

    @Column(name="roadNameAddress")
    private String roadNameAddress;

    @Column(name = "jibunAddress")
    private String jibunAddress; // 지번주소

    // 연관관계의 주인 -> endPos
    @JsonIgnore
    @ManyToOne
    private User user;

    public EndPos(EndPosDto form) {
        this.endX = form.getEndX();
        this.endY = form.getEndY();
        this.endNickName = form.getEndNickName();
        this.roadNameAddress = form.getRoadNameAddress();
        this.jibunAddress = form.getJibunAddress();
    }

    public void setUser(User user) {
        this.user = user;
    }

    //entity -> dto 변환
    public EndPosDto toDto(EndPos entity) {
        EndPosDto dto = new EndPosDto();
        dto.setEndX(entity.getEndX());
        dto.setEndY(entity.getEndY());
        dto.setEndNickName(entity.getEndNickName());
        dto.setRoadNameAddress(entity.getRoadNameAddress());
        dto.setJibunAddress(entity.getJibunAddress());
        return dto;
    }

}
