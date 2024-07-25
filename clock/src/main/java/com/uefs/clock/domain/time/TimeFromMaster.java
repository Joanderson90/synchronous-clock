package com.uefs.clock.domain.time;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeFromMaster {
    private Double value;
    private Boolean mustBeMaster;

}
