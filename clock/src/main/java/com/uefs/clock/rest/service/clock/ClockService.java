package com.uefs.clock.rest.service.clock;

import com.uefs.clock.data.DataClock;
import com.uefs.clock.domain.enums.Clock;
import com.uefs.clock.domain.poll.StartingPoll;
import com.uefs.clock.domain.time.Time;
import com.uefs.clock.domain.time.TimeFromMaster;
import com.uefs.clock.rest.client.WebClientClockI;
import com.uefs.clock.rest.config.ClockApiConfig;
import com.uefs.clock.rest.dto.ClockDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ClockService implements ClockI {

    @Autowired
    ClockApiConfig clockApiConfig;

    @Autowired
    WebClientClockI webClientClock;

    @Override
    public TimeFromMaster getTime(Time time) {

        if (DataClock.isMaster()) {
            boolean clockExternalMustBeMaster = time.getValue() > DataClock.getCurrentTime();

            if (clockExternalMustBeMaster) {
                try {
                    log.info("Setting {} as new master because has the greater time.",
                            getClockNameByClockCode(time.getClockCode()));

                    DataClock.setMasterCode(time.getClockCode());

                    webClientClock.setAsMaster(time.getClockCode());

                    DataClock.setIsMaster(false);

                    getAnotherClockCodeList().stream()
                            .filter(clockCode -> !clockCode.equals(time.getClockCode()))
                            .toList()
                            .forEach(clockCodeFiltered -> webClientClock.setMasterCode(time.getClockCode(), clockCodeFiltered));

                } catch (Exception e) {
                    log.error("Error to set:{} as master", time.getClockCode());
                }
            }

            return new TimeFromMaster(getTime(), clockExternalMustBeMaster);
        }

        throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED,
                String.format("%s is not the master clock.", getThisClock().getName()));
    }

    @Override
    public Double getTime() {
        return DataClock.getCurrentTime();
    }

    @Override
    public Clock setAsMaster() {

        log.info("Setting {} as master...", getThisClock().getName());

        DataClock.setIsMaster(true);

        return getThisClock();
    }

    @Override
    public void setMasterCode(Integer clockCodeMaster) {
        log.info("Setting {} as new master...", getClockNameByClockCode(clockCodeMaster));

        DataClock.setMasterCode(clockCodeMaster);
    }

    @Override
    public Clock getThisClock() {
        return Arrays
                .stream(Clock.values()).filter(clock -> clock.getCode().equals(Integer.parseInt(clockApiConfig.getClockCode())))
                .findFirst()
                .orElseThrow();

    }

    @Override
    public Time updateDrift(Double drift) {
        return DataClock.updateDrift(drift);
    }

    @Override
    public Boolean isMaster() {
        return DataClock.isMaster();
    }

    @Override
    public Boolean setStartingPoll(StartingPoll startingPoll) {

        if (Boolean.TRUE.equals(DataClock.getStartingPoll()) && Boolean.TRUE.equals(startingPoll.getIsStaringPoll())) {
            log.info("Poll already start by: {}", getThisClock().getName());

            return true;
        }

        if (Boolean.TRUE.equals(startingPoll.getIsStaringPoll())) {
            log.info("Starting new poll by: {}", startingPoll.getClockNameStartPoll());
        } else {
            log.info("Poll finished with: {} as master", getClockNameByClockCode(DataClock.getMasterCode()));
        }

        DataClock.isStartingPoll(startingPoll.getIsStaringPoll());

        return false;
    }

    @Override
    public Boolean alreadyStartPoll() {
        return DataClock.getStartingPoll();
    }

    @Override
    public ClockDTO getClockInfo() {
        return ClockDTO.builder()
                .clockName(getThisClock().getName())
                .isMaster(DataClock.isMaster())
                .value(DataClock.getCurrentTime())
                .drift(DataClock.getCurrentDrift())
                .build();
    }

    @Override
    public List<Integer> getAnotherClockCodeList() {
        Integer thisClockCode = getThisClock().getCode();

        return Arrays.stream(Clock.values()).map(Clock::getCode).filter(code -> !code.equals(thisClockCode)).toList();
    }

    @Override
    public Time updateTimeValue(Double value) {
        DataClock.updateTimeValue(value);

        return DataClock.getTime();
    }

    @Override
    public String getClockNameByClockCode(Integer clockCode) {
        return Arrays
                .stream(Clock.values()).filter(clock -> clock.getCode().equals(clockCode))
                .findFirst()
                .map(Clock::getName)
                .orElseThrow();
    }
}
