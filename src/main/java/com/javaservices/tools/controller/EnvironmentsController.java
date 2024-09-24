package com.javaservices.tools.controller;

import com.javaservices.network.clients.HttpClientUtil;
import com.javaservices.tools.dhl.DhlModel;
import com.javaservices.tools.model.ToolsModel;
import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.model.environments.Environment;
import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.servers.Server;
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
public class EnvironmentsController {

    private final ModelController modelController;

    public EnvironmentsController(ModelController modelController) {
        this.modelController = modelController;
    }

    public List<Environment> getEnvironments() {
        return modelController.getModel().getEnvironments();
    }

    public Environment findEnvironmentById(Long id) {
        return this.getEnvironments().stream().filter(environment -> Objects.equals(environment.getId(), id)).findFirst().orElse(null);
    }

    public void updateEnvironment(Environment environment) {
        // TODO save environment according saving method - file / database

        if (environment.getId() == null) {
            Long maxId = this.modelController.getModel().getServers().stream()
                    .max(Comparator.comparingLong(Server::getId))
                    .map(Server::getId)
                    .orElse(0L);
            environment.setId(maxId + 1);

            this.modelController.getModel().getEnvironments().add(environment);
        } else {

        }
    }

}
