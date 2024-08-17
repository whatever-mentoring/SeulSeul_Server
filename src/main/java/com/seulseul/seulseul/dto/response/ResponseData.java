package com.seulseul.seulseul.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseData {
	private int code;
	private Object data;

	public ResponseData(int code, Object data) {
		this.code = code;
		this.data = data;
	}
}