package com.seulseul.seulseul.dto.baseRoute;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class BaseRouteDto {
    private int startStation;
    private int endStation;
    private String firstStation;
    private String lastStation;
}