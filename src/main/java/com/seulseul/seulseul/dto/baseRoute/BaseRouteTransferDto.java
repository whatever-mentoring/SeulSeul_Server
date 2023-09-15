package com.seulseul.seulseul.dto.baseRoute;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter //dto.get() 사용 가능
@Setter //dto.set() 사용 가능
public class BaseRouteTransferDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("startStationId")
    private int startStationId;

    @JsonProperty("endStationId")
    private int endStationId;

    @JsonProperty("exSID")
    private List<Integer> exSID;
}
