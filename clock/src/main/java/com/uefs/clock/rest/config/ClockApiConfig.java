package com.uefs.clock.rest.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Getter
public class ClockApiConfig {

    @Value("${com.uefs.clock.clock-code}")
    private String clockCode;

    @Value("${com.uefs.clock.ip-address-clock-1}")
    private String ipAddressBigBen;

    @Value("${com.uefs.clock.ip-address-clock-2}")
    private String ipAddressShepherdGateClock;

    @Value("${com.uefs.clock.ip-address-clock-3}")
    private String ipAddressFloralClock;


}
