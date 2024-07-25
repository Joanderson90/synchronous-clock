package com.uefs.clock.domain.time;

import lombok.Data;

@Data
public class Time {
    private Integer clockCode;
    private Double value = 0.0;
    private Double drift = 1.0;

    public void updateTime() {
        this.value += 1;
    }
}
