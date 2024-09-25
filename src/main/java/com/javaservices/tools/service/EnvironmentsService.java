package com.javaservices.tools.service;

import com.javaservices.tools.model.environments.Environment;
import com.javaservices.tools.model.servers.Server;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EnvironmentsService {

    private final ModelService modelService;

    public EnvironmentsService(ModelService modelService) {
        this.modelService = modelService;
    }

    public List<Environment> getEnvironments() {
        return modelService.getModel().getEnvironments();
    }

    public Environment findEnvironmentById(Long id) {
        if (this.getEnvironments() == null)
            return null;

        return this.getEnvironments().stream()
                .filter(environment -> Objects.equals(environment.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public void updateEnvironment(Environment environment) {
        // TODO save environment according saving method - file / database

        if (environment.getId() == null) {
            Long maxId = this.modelService.getModel().getServers().stream()
                    .max(Comparator.comparingLong(Server::getId))
                    .map(Server::getId)
                    .orElse(0L);
            environment.setId(maxId + 1);

            this.modelService.getModel().getEnvironments().add(environment);
        } else {

        }
    }

    public void delete(Long id) {
        Environment environment = findEnvironmentById(id);

        if (environment != null) {
            this.modelService.getModel().getEnvironments().remove(environment);
        }
    }
}
