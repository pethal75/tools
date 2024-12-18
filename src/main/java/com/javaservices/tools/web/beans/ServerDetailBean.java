package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.servers.Login;
import com.javaservices.tools.model.servers.Server;
import com.javaservices.tools.service.ServersService;
import com.javaservices.tools.web.beans.primefaces.PrimefacesBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@ViewScoped
@Getter
@Setter
@Slf4j
public class ServerDetailBean extends PrimefacesBean {

    public static final String pageUrl = "serverDetail.xhtml";

    protected ServersService serversService;

    @Value("#{request.getParameter('id')}")
    @ManagedProperty("id")
    protected Long id;

    protected Server server;

    @Inject
    public ServerDetailBean(ServersService serversService) {
        this.serversService = serversService;
    }

    @PostConstruct
    public void init() {
        log.debug("initialize server id : {}", id);

        this.server = serversService.findServerById(id);

        if (this.server != null)
            log.debug("Found server named : {}", server.getName());
        else
            this.server = new Server();

        if (server.getLogin() == null)
            server.setLogin(new Login());
    }

    public Map<String, Server.Protocol> getProtocols() {
        return Arrays.stream(Server.Protocol.values())
                .collect(Collectors.toMap(Enum::toString, protocol -> protocol));
    }

    public Map<String, Server.ServerType> getTypes() {
        return Arrays.stream(Server.ServerType.values())
                .collect(Collectors.toMap(Enum::toString, serverType -> serverType));
    }

    public void save() throws IOException {
        log.debug("Saving server details {}", this.server.getId());

        this.serversService.updateServer(this.server);

        this.redirect(ServersListBean.pageUrl);
    }

    public void cancel() throws IOException {
        this.redirect(ServersListBean.pageUrl);
    }
}
