package com.seulseul.seulseul.dto.Response;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResponseData {
    private int code;
    private Object data;

    public ResponseData(int code,Object data) {
        this.code = code;
        this.data = data;
    }
}