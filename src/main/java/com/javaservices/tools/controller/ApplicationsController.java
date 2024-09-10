package com.javaservices.tools.controller;

import com.javaservices.network.clients.HttpClientUtil;
import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.dhl.DhlModel;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ApplicationsController {

    // TODO read from session storage
    protected DhlModel dhlConfiguration = new DhlModel();

    protected HttpClientUtil httpClientUtil = new HttpClientUtil();

    @PostConstruct
    public void init() {
        this.refreshApplications();
    }

    /**
     * Reloads actual statuses for all applications, subscribes for responses and refresh main configuration array
     */
    public void refreshApplications() {
        List<Mono<ResponseEntity<String>>> responses = dhlConfiguration.getApplications().stream()
                .flatMap(applicationConfiguration -> applicationConfiguration.getInstances().stream())
                .map(this::refreshApplicationStatus)
                .toList();

        Flux.zip(responses, Arrays::asList)
                .flatMapIterable(objects -> objects)
                .subscribe();
    }

    /**
     * Reload status for specific application and returns Mono object to be subscribed for response
     *
     * @param instance application config
     * @return Mono with ResponseEntity
     */
    public Mono<ResponseEntity<String>> refreshApplicationStatus(ApplicationInstance instance) {
        log.debug("Check application status {} {}", instance.getApplication().getName(), instance.getApplicationUrl());

        return httpClientUtil.httpCall(instance.getApplicationUrl(), "GET")
            .exchangeToMono(response -> {
                if (response.statusCode().is2xxSuccessful()) {
                    log.debug("Check application status OK {} {}", instance.getApplication().getName(), instance.getApplicationUrl());

                    instance.setStatus(ApplicationInstance.Status.UP);
                } else {
                    log.debug("Check application status NOT OK {} {}", instance.getApplication().getName(), instance.getApplicationUrl());

                    instance.setStatus(ApplicationInstance.Status.DOWN);
                }

                return response.toEntity(String.class);
            })
            .doOnError(throwable -> {
                log.debug("Check application status ERROR {} {}", instance.getApplication().getName(), instance.getApplicationUrl());

                instance.setStatus(ApplicationInstance.Status.DOWN);
            })
            .onErrorReturn(ResponseEntity.notFound().build());
    }

    public List<Application> getApplications() {
        return dhlConfiguration.getApplications();
    }

    public List<ApplicationInstance> getApplicationInstances() {
        return dhlConfiguration.getApplications().stream()
                .flatMap(applicationConfiguration -> applicationConfiguration.getInstances().stream())
                .collect(Collectors.toList());
    }

    public Application findApplicationByName(String name) {
        return dhlConfiguration.getApplications().stream()
                .filter(application -> application.getName().equals(name))
                .findFirst().orElse(null);
    }
}
