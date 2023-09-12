package com.seulseul.seulseul.dto.baseRoute;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class BaseRouteJsonDto {
    private Result result;

    @Getter
    @NoArgsConstructor
    public static class Result {
        private List<Path> path;
    }

    @Getter
    @NoArgsConstructor
    public static class Path {
        private Info info;
        private List<SubPath> subPath;
    }

    @Getter
    @NoArgsConstructor
    public static class Info {
        private String firstStartStation;
        private String lastEndStation;
    }

    @Getter
    @NoArgsConstructor
    public static class SubPath {
        private int startID;
        private int endID;
    }
}
