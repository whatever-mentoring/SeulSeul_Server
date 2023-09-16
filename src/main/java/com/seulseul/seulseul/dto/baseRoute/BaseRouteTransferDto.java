package com.seulseul.seulseul.dto.baseRoute;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter //dto.get() 사용 가능
public class BaseRouteTransferDto {
    private Result result;

    @Getter
    @NoArgsConstructor
    public class Result {
        @JsonProperty("exChangeInfoSet")
        private DriveInfoSet driveInfoSet;
        private ExChangeInfoSet exChangeInfoSet;

    }

    @Getter
    @NoArgsConstructor
    public class DriveInfoSet {
        @JsonProperty("driveInfo")
        private List<DriveInfo> driveInfo;
    }

    @Getter
    @NoArgsConstructor
    public class DriveInfo {

        @JsonProperty("laneName")
        private String laneName;

        @JsonProperty("wayCode")
        private int wayCode;

        @JsonProperty("wayName")
        private String wayName;

    }
    @Getter
    @NoArgsConstructor
    public class ExChangeInfoSet {
        @JsonProperty("exChangeInfo")
        private List<ExChangeInfo> exChangeInfo;

    }
    @Getter
    @NoArgsConstructor
    public class ExChangeInfo {

        private String exName;
        private int exSID;

        private int fastDoor;
        private int exWalkTime;
    }

}
