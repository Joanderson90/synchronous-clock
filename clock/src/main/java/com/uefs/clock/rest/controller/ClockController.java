package com.uefs.clock.rest.controller;

import com.uefs.clock.domain.enums.Clock;
import com.uefs.clock.domain.poll.StartingPoll;
import com.uefs.clock.domain.time.Time;
import com.uefs.clock.domain.time.TimeFromMaster;
import com.uefs.clock.rest.dto.ClockDTO;
import com.uefs.clock.rest.service.clock.ClockI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clock")
public class ClockController {

    @Autowired
    private ClockI clockService;

    @GetMapping("currentTimeFromMaster")
    public TimeFromMaster getCurrentTimeFromMaster(@RequestBody Time time) {
        return clockService.getTime(time);
    }

    @GetMapping
    public Double getCurrentTime() {
        return clockService.getTime();
    }

    @GetMapping("clockInfo")
    public ClockDTO getClockInfo() {
        return clockService.getClockInfo();
    }

    @GetMapping("isMaster")
    public Boolean isMaster() {
        return clockService.isMaster();
    }

    @GetMapping("alreadyStartPoll")
    public Boolean alreadyStartPoll() {
        return this.clockService.alreadyStartPoll();
    }

    @PostMapping("setAsMaster")
    public Clock setAsMaster() {
        return this.clockService.setAsMaster();
    }

    @PostMapping("setMasterCode/{masterCode}")
    public void setMasterCode(@PathVariable Integer masterCode) {
        this.clockService.setMasterCode(masterCode);
    }

    @PostMapping("isStartingPoll")
    public Boolean setIsStartingPoll(@RequestBody StartingPoll startingPoll) {
        return this.clockService.setStartingPoll(startingPoll);
    }


    @PatchMapping("updateDrift")
    public Time updateDrift(@RequestParam Double drift) {
        return this.clockService.updateDrift(drift);
    }

    @PatchMapping("updateTimeValue")
    public Time updateTimeValue(@RequestParam Double value) {
        return this.clockService.updateTimeValue(value);
    }

}
