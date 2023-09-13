package com.seulseul.seulseul.entity.baseRoute;

import com.seulseul.seulseul.dto.baseRoute.BaseRouteDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="BaseRoute")
public class BaseRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_x")
    private double startx;

    @Column(name="start_y")
    private double starty;

    @Column(name = "end_x")
    private double endx;

    @Column(name="end_y")
    private double endy;

    //entity -> dto 변환
    public BaseRouteDto toDto(BaseRoute entity) {
        BaseRouteDto dto = new BaseRouteDto();
        dto.setId(entity.getId());
        dto.setStartx(entity.getStartx());
        dto.setStarty(entity.getStarty());
        dto.setEndx(entity.getEndx());
        dto.setEndy(entity.getEndy());
        return dto;
    }
}
