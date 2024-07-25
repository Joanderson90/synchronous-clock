package com.uefs.clock.data;

import com.uefs.clock.domain.time.Time;
import lombok.Getter;
import lombok.Setter;

public class DataClock {

    @Getter
    private static Time time = new Time();

    @Getter
    private static boolean isMaster = false;

    @Getter
    @Setter
    private static Integer masterCode;

    @Getter
    @Setter
    private static Boolean startingPoll = Boolean.FALSE;


    public static void setIsMaster(boolean isMaster) {
        DataClock.isMaster = isMaster;
    }

    public static void updateTime() {
        DataClock.time.updateTime();
    }

    public static void updateTimeValue(Double value) {
        DataClock.time.setValue(value);
    }

    public static Time updateDrift(Double drift) {

        DataClock.time.setDrift(drift);

        return DataClock.time;
    }

    public static Double getCurrentTime() {
        return DataClock.time.getValue();
    }

    public static Double getCurrentDrift() {
        return DataClock.time.getDrift();
    }

    public static void isStartingPoll(Boolean isStaringPoll) {
        DataClock.startingPoll = isStaringPoll;
    }

}
