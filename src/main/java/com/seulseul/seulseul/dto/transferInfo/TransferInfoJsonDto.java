package com.seulseul.seulseul.dto.transferInfo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;

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
