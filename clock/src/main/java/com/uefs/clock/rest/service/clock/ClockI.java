package com.uefs.clock.rest.service.clock;

import com.uefs.clock.domain.enums.Clock;
import com.uefs.clock.domain.poll.StartingPoll;
import com.uefs.clock.domain.time.Time;
import com.uefs.clock.domain.time.TimeFromMaster;
import com.uefs.clock.rest.dto.ClockDTO;

import java.util.List;

public interface ClockI {

    TimeFromMaster getTime(Time time);

    Double getTime();

    Clock setAsMaster();

    void setMasterCode(Integer clockCodeMaster);

    Clock getThisClock();

    Time updateDrift(Double drift);

    Boolean isMaster();

    Boolean setStartingPoll(StartingPoll startingPoll);

    Boolean alreadyStartPoll();

    ClockDTO getClockInfo();

    List<Integer> getAnotherClockCodeList();

    Time updateTimeValue(Double value);

    String getClockNameByClockCode(Integer clockCode);


}
