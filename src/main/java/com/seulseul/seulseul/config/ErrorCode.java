package com.seulseul.seulseul.config;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // 직접 작성하며 추가
    BAD_REQUEST(400, "잘못된 문법입니다."),
    NOT_FOUND(404, "요청한 리소스가 서버에 없습니다."),
    CONFLICT(409, "요청한 리소스가 서버와 충돌합니다."),
    USER_NOT_FOUND(410, "유저가 존재하지 않습니다."),
    BASEROUTE_NOT_FOUND(411, "저장된 경로가 없습니다."),
    ALARM_NOT_FONUD(412, "알림 시간 및 간격이 세팅되어 있지 않습니다."),
    ENDPOS_NOT_FOUND(413, "현재 사용자가 저장한 목적지가 아닙니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
