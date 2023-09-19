package com.seulseul.seulseul.dto.stopTimeList;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteTransferDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class StopTimeDto {
    private Result result;

    @Getter
    @NoArgsConstructor
    public class Result {
        @JsonProperty("OrdList")
        private OrdList ordList;
        @JsonProperty("SatList")
        private SatList satList;

        @JsonProperty("SunList")
        private SunList sunList;

    }

    @Getter
    @NoArgsConstructor
    public class OrdList {
        @JsonProperty("up")
        private Up up;

        @JsonProperty("down")
        private Down down;

    }
    @Getter
    @NoArgsConstructor
    public class SatList {
        @JsonProperty("up")
        private Up up;

        @JsonProperty("down")
        private Down down;
    }

    @Getter
    @NoArgsConstructor
    public class SunList {
        @JsonProperty("up")
        private Up up;

        @JsonProperty("down")
        private Down down;
    }
    @Getter
    @NoArgsConstructor
    public class Up {
        @JsonProperty("time")
        private List<Time> time;
    }

    @Getter
    @NoArgsConstructor
    public class Down {
        @JsonProperty("time")
        private List<Time> time;
    }

    @Getter
    @NoArgsConstructor
    public class Time {
        @JsonProperty("idx")
        private Integer idx;
        @JsonProperty("list")
        private String list;
    }


}
