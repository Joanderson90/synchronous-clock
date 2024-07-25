package com.uefs.clock.rest.client;

import com.uefs.clock.domain.poll.StartingPoll;
import com.uefs.clock.domain.time.Time;
import com.uefs.clock.domain.time.TimeFromMaster;

public interface WebClientClockI {

    TimeFromMaster getCurrentTimeFromMaster(Time time, Integer clockCode);

    Boolean getIsMaster(Integer clockCode);

    Boolean setStartingPoll(StartingPoll startingPoll, Integer clockCode);

    void setMasterCode(Integer clockCodeMaster, Integer clockCode);

    void setAsMaster(Integer clockCode);

    Boolean getAlreadyStartPoll(Integer clockCode);


}
