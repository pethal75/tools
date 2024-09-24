package com.javaservices.tools.controller;

import com.javaservices.tools.dhl.DhlModel;
import com.javaservices.tools.model.ToolsModel;
import com.javaservices.tools.model.servers.Server;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ServersController {

    // TODO read from session storage
    protected ToolsModel toolsModel = new DhlModel();

    public List<Server> getServers() {
        return toolsModel.getServers();
    }

    public Server findServerById(Long id) {
        return this.getServers().stream().filter(server -> Objects.equals(server.getId(), id)).findFirst().orElse(null);
    }

    public void updateServer(Server server) {
        // TODO save server according saving method - file / database

        if (server.getId() == null) {
            Long maxId = this.toolsModel.getServers().stream().max(Comparator.comparingLong(Server::getId)).map(Server::getId).orElse(0L);
            server.setId(maxId + 1);
        }

        this.toolsModel.getServers().add(server);
    }
}
