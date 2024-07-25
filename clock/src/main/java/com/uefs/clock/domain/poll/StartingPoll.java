package com.uefs.clock.domain.poll;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartingPoll {
    @JsonProperty("clock_name_start_poll")
    private String clockNameStartPoll;

    @JsonProperty("is_staring_poll")
    private Boolean isStaringPoll;
}
