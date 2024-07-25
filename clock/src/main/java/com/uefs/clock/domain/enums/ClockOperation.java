package com.uefs.clock.domain.enums;

import lombok.Getter;

@Getter
public enum ClockOperation {
    CURRENT_TIME_FROM_MASTER("currentTimeFromMaster"),
    IS_MASTER("isMaster"),
    SET_MASTER_CODE("setMasterCode"),
    IS_STARTING_POLL("isStartingPoll"),
    SET_AS_MASTER("setAsMaster"),
    ALREADY_START_POLL("alreadyStartPoll");


    private final String operationValue;

    ClockOperation(String operation) {
        this.operationValue = operation;
    }
}
