package com.uefs.clock.rest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClockDTO {
    private String clockName;
    private Boolean isMaster;
    private Double value;
    private Double drift;

}
