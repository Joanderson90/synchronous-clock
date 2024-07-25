package com.uefs.clock.domain.enums;

import com.uefs.clock.rest.config.ClockApiConfig;
import lombok.Getter;

@Getter
public enum Clock {
    BIG_BEN(1, "Big Ben", "http://", 8080),
    SHEPHERD_GATE_CLOCK(2, "Shepherd Gate Clock", "http://", 8081),
    FLORAL_CLOCK(3, "Floral Clock", "http://", 8082);


    private final Integer code;

    private final String name;

    private final String url;

    private final Integer port;

    Clock(int code, String name, String url, int port) {
        this.code = code;
        this.name = name;
        this.url = url;
        this.port = port;
    }


    public String getCompleteUrl(ClockApiConfig clockApiConfig) {

        String ipAddressClock = switch (this.code) {
            case 1 -> clockApiConfig.getIpAddressBigBen();
            case 2 -> clockApiConfig.getIpAddressShepherdGateClock();
            case 3 -> clockApiConfig.getIpAddressFloralClock();
            default -> "localhost";
        };


        return this.getUrl() + ipAddressClock + ":" + this.getPort() + "/api/clock/";
    }

}
