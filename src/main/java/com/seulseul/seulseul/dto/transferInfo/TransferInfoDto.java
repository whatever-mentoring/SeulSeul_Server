package com.seulseul.seulseul.dto.transferInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class TransferInfoDto {
    private int exSID;
    private String exName;
    private int exWalkTime;
    private int travelTime;
}
