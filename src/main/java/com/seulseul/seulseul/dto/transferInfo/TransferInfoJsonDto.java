package com.seulseul.seulseul.dto.transferInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seulseul.seulseul.dto.baseRoute.BaseRouteJsonDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class TransferInfoJsonDto {
    private Result result;

    @Getter
    @NoArgsConstructor
    public static class Result {
        private ExChangeInfoSet exChangeInfoSet;
    }

    @Getter
    @NoArgsConstructor
    public static class ExChangeInfoSet {
        private List<ExChangeInfo> exChangeInfo;
    }

    @Getter
    @NoArgsConstructor
    public static class ExChangeInfo {
        private String exName;
        private int exWalkTime;
        private int exSID;
    }
}
