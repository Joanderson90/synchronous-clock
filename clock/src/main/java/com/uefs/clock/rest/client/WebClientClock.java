package com.uefs.clock.rest.client;

import com.uefs.clock.domain.enums.Clock;
import com.uefs.clock.domain.enums.ClockOperation;
import com.uefs.clock.domain.poll.StartingPoll;
import com.uefs.clock.domain.time.Time;
import com.uefs.clock.domain.time.TimeFromMaster;
import com.uefs.clock.rest.config.ClockApiConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class WebClientClock implements WebClientClockI {

    @Autowired
    private ClockApiConfig clockApiConfig;

    private final WebClient webClient;


    public WebClientClock() {
        HttpClient httpClient = HttpClient.create();
        this.webClient = WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).build();
    }


    @Override
    public TimeFromMaster getCurrentTimeFromMaster(Time time, Integer clockCode) {

        AtomicReference<TimeFromMaster> timeFromMaster = new AtomicReference<>(new TimeFromMaster());

        Arrays.stream(Clock.values()).filter(clock -> clock.getCode().equals(clockCode)).findFirst().ifPresent(clock -> {
            try {
                timeFromMaster.set(getCurrentTimeFromMasterRequestAsync(clock.getCompleteUrl(clockApiConfig) +
                        ClockOperation.CURRENT_TIME_FROM_MASTER.getOperationValue(), time));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return timeFromMaster.get();
    }

    @Override
    public Boolean getIsMaster(Integer clockCode) {

        AtomicReference<Boolean> isMaster = new AtomicReference<>(Boolean.FALSE);

        Arrays.stream(Clock.values())
                .filter(clock -> clock.getCode().equals(clockCode))
                .findFirst()
                .ifPresent(clock -> isMaster.set(getIsMasterRequestAsync(clock.getCompleteUrl(clockApiConfig) +
                        ClockOperation.IS_MASTER.getOperationValue())));

        return isMaster.get();
    }

    @Override
    public Boolean setStartingPoll(StartingPoll startingPoll, Integer clockCode) {

        AtomicReference<Boolean> isStartingPoll = new AtomicReference<>(Boolean.FALSE);

        try {
            Arrays.stream(Clock.values())
                    .filter(clock -> clock.getCode().equals(clockCode))
                    .findFirst()
                    .ifPresent(clock -> isStartingPoll.set(Boolean.valueOf(postSynchronously(clock.getCompleteUrl(clockApiConfig) +
                            ClockOperation.IS_STARTING_POLL.getOperationValue(), startingPoll))));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return isStartingPoll.get();

    }

    @Override
    public void setMasterCode(Integer clockCodeMaster, Integer clockCode) {
        try {
            Arrays.stream(Clock.values())
                    .filter(clock -> clock.getCode().equals(clockCode))
                    .findFirst()
                    .ifPresent(clock -> postSynchronously(clock.getCompleteUrl(clockApiConfig) +
                            ClockOperation.SET_MASTER_CODE.getOperationValue() + "/" + clockCodeMaster, clockCodeMaster));
        } catch (Exception e) {
            log.error(e.getMessage());
        }


    }

    @Override
    public void setAsMaster(Integer clockCode) {
        try {
            Arrays.stream(Clock.values())
                    .filter(clock -> clock.getCode().equals(clockCode))
                    .findFirst()
                    .ifPresent(clock -> postSynchronously(clock.getCompleteUrl(clockApiConfig) +
                            ClockOperation.SET_AS_MASTER.getOperationValue(), clockCode));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Boolean getAlreadyStartPoll(Integer clockCode) {

        AtomicReference<Boolean> alreadyStartPoll = new AtomicReference<>(Boolean.FALSE);

        try {
            Arrays.stream(Clock.values())
                    .filter(clock -> clock.getCode().equals(clockCode))
                    .findFirst()
                    .ifPresent(clock -> alreadyStartPoll.set(getAlreadyStartPollRequestAsync(clock.getCompleteUrl(clockApiConfig) +
                            ClockOperation.ALREADY_START_POLL.getOperationValue())));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return alreadyStartPoll.get();
    }


    public TimeFromMaster getCurrentTimeFromMasterRequestAsync(String url, Object requestBody) {

        TimeFromMaster timeFromMasterResponse = new TimeFromMaster();

        try {
            timeFromMasterResponse = this.webClient
                    .method(HttpMethod.GET)
                    .uri(url)
                    .accept(MediaType.ALL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(requestBody), Object.class)
                    .retrieve()
                    .bodyToMono(TimeFromMaster.class)
                    .block();

        } catch (Exception ex) {
            throw new RuntimeException("XYZ service api error: " + ex.getMessage());
        } finally {
            log.info("API Response {}", timeFromMasterResponse);
        }

        return timeFromMasterResponse;
    }

    public Boolean getAlreadyStartPollRequestAsync(String url) {

        Boolean alreadyStartPoll = Boolean.FALSE;

        try {
            alreadyStartPoll = this.webClient
                    .method(HttpMethod.GET)
                    .uri(url)
                    .accept(MediaType.ALL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

        } catch (Exception ex) {
            log.error("Error while calling API ", ex);
        } finally {
            log.info("API Response {}", alreadyStartPoll);
        }

        return alreadyStartPoll;
    }

    public Boolean getIsMasterRequestAsync(String url) {

        Boolean isMaster = Boolean.FALSE;

        try {
            isMaster = this.webClient
                    .method(HttpMethod.GET)
                    .uri(url)
                    .accept(MediaType.ALL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

        } catch (Exception ex) {
            log.error("Error while calling API ", ex);
        } finally {
            log.info("API Response {}", isMaster);
        }

        return isMaster;
    }

    public String postSynchronously(String url, Object requestBody) {

        String response = "";

        try {
            response = this.webClient
                    .method(HttpMethod.POST)
                    .uri(url)
                    .accept(MediaType.ALL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(requestBody), Object.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception ex) {
            throw new RuntimeException("XYZ service api error: " + ex.getMessage());
        } finally {
            log.info("API Response {}", response);
        }

        return response;
    }
}
