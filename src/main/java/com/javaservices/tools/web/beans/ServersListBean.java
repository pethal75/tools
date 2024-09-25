package com.javaservices.tools.web.beans;

import com.javaservices.tools.service.ServersService;
import com.javaservices.tools.model.servers.Server;
import jakarta.el.MethodExpression;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
@ViewScoped
public class ServersListBean {

    public static final String pageUrl = "serversList.xhtml";

    protected final ServersService serversService;

    @Inject
    public ServersListBean(ServersService serversService) {
        this.serversService = serversService;
    }

    public List<Server> getServers() {
        return serversService.getServers();
    }

    public void deleteServer(Long id) {
        this.serversService.delete(id);
    }
}
