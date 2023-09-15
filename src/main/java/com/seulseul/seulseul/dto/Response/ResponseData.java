package com.seulseul.seulseul.dto.Response;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResponseData {
    private boolean success;
    private int code;
    private String message;
    private Object data;

    public ResponseData(boolean success, int code, String message, Object data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}