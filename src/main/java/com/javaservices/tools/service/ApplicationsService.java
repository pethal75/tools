package com.javaservices.tools.service;

import com.javaservices.network.clients.HttpClientUtil;
import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class ApplicationsService {

    private final ModelService modelService;

    protected final HttpClientUtil httpClientUtil = new HttpClientUtil();

    public ApplicationsService(ModelService modelService) {
        this.modelService = modelService;
    }

    @PostConstruct
    public void init() {
        this.refreshApplications();
    }

    /**
     * Reloads actual statuses for all applications, subscribes for responses and refresh main configuration array
     */
    public void refreshApplications() {
        List<Mono<ResponseEntity<String>>> responses = modelService.getModel().getApplications().stream()
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
                    log.debug("Check application status OK {} {} {}", instance.getApplication().getName(), instance.getName(), instance.getApplicationUrl());

                    instance.setStatus(ApplicationInstance.Status.UP);
                } else {
                    log.debug("Check application status NOT OK {} {} {}", instance.getApplication().getName(), instance.getName(), instance.getApplicationUrl());

                    instance.setStatus(ApplicationInstance.Status.DOWN);
                }

                return response.toEntity(String.class);
            })
            .doOnError(throwable -> {
                log.debug("Check application status ERROR {} {} {} {}", instance.getApplication().getName(), instance.getName(), instance.getApplicationUrl(), throwable.getMessage());

                instance.setStatus(ApplicationInstance.Status.DOWN);
            })
            .onErrorReturn(ResponseEntity.notFound().build());
    }

    public List<Application> getApplications() {
        return modelService.getModel().getApplications();
    }

    public List<ApplicationInstance> getApplicationInstances() {

        if (this.getApplications() == null)
            return Collections.emptyList();

        return this.getApplications().stream()
                .flatMap(applicationConfiguration -> applicationConfiguration.getInstances().stream())
                .collect(Collectors.toList());
    }

    public Application findApplicationByName(String name) {
        if (this.getApplications() == null)
            return null;

        return this.getApplications().stream()
                .filter(application -> application.getName().equals(name))
                .findFirst().orElse(null);
    }

    public Application findApplicationById(Long id) {
        if (this.getApplications() == null)
            return null;

        return this.getApplications().stream()
                .filter(application -> Objects.equals(application.getId(), id))
                .findFirst().orElse(null);
    }


    public ApplicationInstance findApplicationInstanceById(Long id) {
        if (this.getApplicationInstances() == null)
            return null;

        return this.getApplicationInstances().stream()
                .filter(application -> Objects.equals(application.getId(), id))
                .findFirst().orElse(null);
    }

    public void updateApplication(Application application) {
        // TODO save group according saving method - file / database

        if (application.getId() == null) {
            Long maxId = this.modelService.getModel().getApplications().stream()
                    .max(Comparator.comparingLong(Application::getId))
                    .map(Application::getId)
                    .orElse(0L);
            application.setId(maxId + 1);

            this.modelService.getModel().getApplications().add(application);
        } else {

        }
    }

    public void delete(Long id) {
        Application application = findApplicationById(id);

        if (application != null) {
            this.modelService.getModel().getApplications().remove(application);
        }
    }

    public void updateApplicationInstance(ApplicationInstance applicationInstance) {
        // TODO
    }
}
