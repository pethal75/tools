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

    private final ToolsModelService toolsModelService;

    public ServersService(ToolsModelService toolsModelService) {
        this.toolsModelService = toolsModelService;
    }

    public List<Server> getServers() {
        return toolsModelService.getModel().getServers();
    }

    public Server findServerById(Long id) {
        return this.getServers().stream().filter(server -> Objects.equals(server.getId(), id)).findFirst().orElse(null);
    }

    public void updateServer(Server server) {
        // TODO save server according saving method - file / database

        if (server.getId() == null) {
            Long maxId = this.toolsModelService.getModel().getServers().stream()
                    .max(Comparator.comparingLong(Server::getId))
                    .map(Server::getId)
                    .orElse(0L);
            server.setId(maxId + 1);

            this.toolsModelService.getModel().getServers().add(server);
        } else {
            // TODO update ?
        }
    }

    public void delete(Long id) {
        Server server = findServerById(id);

        if (server != null) {
            this.toolsModelService.getModel().getServers().remove(server);
        }
    }
}
