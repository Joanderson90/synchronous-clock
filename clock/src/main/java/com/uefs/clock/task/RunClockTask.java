package com.uefs.clock.task;

import com.uefs.clock.data.DataClock;
import com.uefs.clock.domain.enums.Clock;
import com.uefs.clock.domain.poll.StartingPoll;
import com.uefs.clock.domain.time.Time;
import com.uefs.clock.domain.time.TimeFromMaster;
import com.uefs.clock.rest.client.WebClientClock;
import com.uefs.clock.rest.config.ClockApiConfig;
import com.uefs.clock.rest.service.clock.ClockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;


@Slf4j
@Configuration
@EnableScheduling
public class RunClockTask {

    @Autowired
    private ClockService clockService;

    @Autowired
    WebClientClock webClientClock;

    @Autowired
    private ClockApiConfig clockApiConfig;

    private static final long FIXED_DELAY = 5000;

    private boolean pollStarted = false;


    @Scheduled(fixedDelay = FIXED_DELAY)
    public void rotateClock() {

        if (Boolean.FALSE.equals(DataClock.getStartingPoll()) && (!DataClock.isMaster())) {

            if (DataClock.getMasterCode() != null) {

                try {

                    Time time = DataClock.getTime();
                    time.setClockCode(clockService.getThisClock().getCode());

                    TimeFromMaster timeFromMaster = webClientClock.getCurrentTimeFromMaster(time, DataClock.getMasterCode());

                    if (Boolean.FALSE.equals(timeFromMaster.getMustBeMaster())) {
                        DataClock.updateTimeValue(timeFromMaster.getValue());
                    }

                } catch (Exception e) {

                    log.info("Master: {} not ok.", DataClock.getMasterCode());

                    if (Boolean.FALSE.equals(DataClock.getStartingPoll())) {
                        chooseNewMaster();
                    }

                }

            } else {
                setMasterCode();

                if (DataClock.getMasterCode() == null) {
                    log.info("Not found anyone as master. Starting poll...");

                    chooseNewMaster();
                }

            }


        }


    }

    private void setMasterCode() {

        for (Integer clockCode : clockService.getAnotherClockCodeList()) {
            Boolean isMaster = webClientClock.getIsMaster(clockCode);

            if (Boolean.TRUE.equals(isMaster)) {
                DataClock.setMasterCode(clockCode);

                break;
            }

        }

    }

    private void chooseNewMaster() {

        setPollAlreadyStarted();

        if (!pollStarted) {
            log.info("Starting poll...");

            DataClock.setStartingPoll(true);

            setToOuthersClockIsStartingPoll();
            setNewMaster();
        }


    }


    private void setPollAlreadyStarted() {

        if (Boolean.TRUE.equals(DataClock.getStartingPoll())) {
            pollStarted = true;

            return;
        }

        for (Integer clockCode : clockService.getAnotherClockCodeList()) {
            Boolean alreadyStartingPoll = webClientClock.getAlreadyStartPoll(clockCode);

            if (Boolean.TRUE.equals(alreadyStartingPoll)) {
                log.info("{} already start poll.", clockCode);

                pollStarted = true;

                break;
            } else {
                pollStarted = false;
            }
        }
    }


    private void setToOuthersClockIsStartingPoll() {

        StartingPoll startingPoll = new StartingPoll(clockService.getThisClock().getName(), true);

        clockService.getAnotherClockCodeList().forEach(clockCode -> webClientClock.setStartingPoll(startingPoll, clockCode));
    }

    private void setNewMaster() {

        for (Clock clock : Arrays.stream(Clock.values()).toList()) {

            if (clock.getCode().equals(clockService.getThisClock().getCode())) {
                setThisClockAsMaster(clock);

                break;

            } else {

                try {
                    setAnotherClockAsMaster(clock);

                    break;

                } catch (Exception e) {
                    log.error("Error to set {} as  new master.", clock.getName());
                    log.info("Try set next master...");
                }

            }
        }


    }

    private void setThisClockAsMaster(Clock clock) {

        DataClock.setIsMaster(true);
        DataClock.setStartingPoll(false);

        clockService.getAnotherClockCodeList().forEach(clockCode -> webClientClock.setMasterCode(clock.getCode(),
                clockCode));

        StartingPoll startingPoll = new StartingPoll(clock.getName(), false);

        clockService.getAnotherClockCodeList().forEach(clockCode -> webClientClock.setStartingPoll(startingPoll,
                clockCode));
    }

    private void setAnotherClockAsMaster(Clock clock) {
        webClientClock.setAsMaster(clock.getCode());

        DataClock.setMasterCode(clock.getCode());
        DataClock.setStartingPoll(false);

        clockService.getAnotherClockCodeList().forEach(clockCode -> webClientClock.setMasterCode(clock.getCode(),
                clockCode));


        StartingPoll startingPoll = new StartingPoll(clockService.getThisClock().getName(), false);

        clockService.getAnotherClockCodeList().forEach(clockCode -> webClientClock.setStartingPoll(startingPoll,
                clockCode));
    }


}
