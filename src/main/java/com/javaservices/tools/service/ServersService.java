package com.javaservices.tools.service;

import com.javaservices.tools.model.servers.Server;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ServersService {

    private final ModelService modelService;

    public ServersService(ModelService modelService) {
        this.modelService = modelService;
    }

    public List<Server> getServers() {
        return modelService.getModel().getServers();
    }

    public Server findServerById(Long id) {
        return this.getServers().stream().filter(server -> Objects.equals(server.getId(), id)).findFirst().orElse(null);
    }

    public void updateServer(Server server) {
        // TODO save server according saving method - file / database

        if (server.getId() == null) {
            Long maxId = this.modelService.getModel().getServers().stream()
                    .max(Comparator.comparingLong(Server::getId))
                    .map(Server::getId)
                    .orElse(0L);
            server.setId(maxId + 1);

            this.modelService.getModel().getServers().add(server);
        } else {
            // TODO update ?
        }
    }

    public void delete(Long id) {
        Server server = findServerById(id);

        if (server != null) {
            this.modelService.getModel().getServers().remove(server);
        }
    }
}
