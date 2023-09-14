package com.seulseul.seulseul.entity.transferInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TransferInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int exSID;

    // 환승역 이름
    private String exName;

    private int exWalkTime;

    private int travelTime;

    public TransferInfo(int exSID, String exName, int exWalkTime) {
        this.exSID = exSID;
        this.exName = exName;
        this.exWalkTime = exWalkTime;
    }
}
