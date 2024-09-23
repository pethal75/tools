package com.javaservices.tools.web.beans;

import com.javaservices.tools.controller.ServersController;
import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.servers.Server;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
@ViewScoped
public class ServersListBean {

    protected final ServersController serversController;

    @Inject
    public ServersListBean(ServersController serversController) {
        this.serversController = serversController;
    }

    public List<Server> getServers() {
        return serversController.getServers();
    }

}
