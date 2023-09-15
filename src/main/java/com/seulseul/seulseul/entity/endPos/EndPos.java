package com.seulseul.seulseul.entity.endPos;

import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import com.seulseul.seulseul.dto.endPos.EndPosDto;
import com.seulseul.seulseul.entity.baseRoute.BaseRoute;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
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
    private double endx;

    @Column(name="end_y")   //위도
    private double endy;

    @Column(name = "endNickName")   //endNickName:안드로이드 => Home: 본가, LivingAlone: 자취방, Dormitory: 기숙사, ??:기타(사용자지정)
    private String endNickName;

    @Column(name="roadNameAddress")
    private String roadNameAddress;

    //entity -> dto 변환
    public EndPosDto toDto(EndPos entity) {
        EndPosDto dto = new EndPosDto();
        dto.setId(entity.getId());
        dto.setEndx(entity.getEndx());
        dto.setEndy(entity.getEndy());
        dto.setEndNickName(entity.getEndNickName());
        dto.setRoadNameAddress(entity.getRoadNameAddress());
        return dto;
    }

}
