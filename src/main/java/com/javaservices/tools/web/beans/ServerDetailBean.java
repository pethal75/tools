package com.javaservices.tools.web.beans;

import com.javaservices.tools.controller.ServersController;
import com.javaservices.tools.model.servers.Server;
import com.javaservices.tools.web.beans.primefaces.PrimefacesBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@EqualsAndHashCode
@Component
@ViewScoped
@Data
@Slf4j
public class ServerDetailBean extends PrimefacesBean {

    public static final String pageUrl = "serverDetail.xhtml";

    protected ServersController serversController;

    @Value("#{request.getParameter('id')}")
    @ManagedProperty("id")
    protected Long id;

    protected Server server;

    @Inject
    public ServerDetailBean(ServersController serversController) {
        this.serversController = serversController;
    }

    @PostConstruct
    public void init() {
        log.debug("initialize application id : {}", id);

        if (this.id == null)
            return;

        this.server = serversController.findServerById(id);

        if (this.server != null)
            log.debug("Found server named : {}", server.getName());
        else
            this.server = new Server();
    }

    public Map<String, Server.Protocol> getProtocols() {
        return Arrays.stream(Server.Protocol.values())
                .collect(Collectors.toMap(protocol -> protocol.toString(), protocol -> protocol));
    }

    public Map<String, Server.ServerType> getTypes() {
        return Arrays.stream(Server.ServerType.values())
                .collect(Collectors.toMap(serverType -> serverType.toString(), serverType -> serverType));
    }

    public void save() throws IOException {
        log.debug("Saving server details {}", this.server.getId());

        // TODO Save configuration to database or disk in the future when loading/saving will be implemented

        this.redirect(ServersListBean.pageUrl);
    }

}
