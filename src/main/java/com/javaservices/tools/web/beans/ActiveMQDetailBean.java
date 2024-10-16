package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.messaging.ActiveMQ;
import com.javaservices.tools.model.servers.Server;
import com.javaservices.tools.service.ActiveMQService;
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
public class ActiveMQDetailBean extends PrimefacesBean {

    public static final String pageUrl = "activemqDetail.xhtml";

    protected ActiveMQService activeMQService;

    @Value("#{request.getParameter('id')}")
    @ManagedProperty("id")
    protected Long id;

    protected ActiveMQ activeMQ;

    @Inject
    public ActiveMQDetailBean(ActiveMQService activeMQService) {
        this.activeMQService = activeMQService;
    }

    @PostConstruct
    public void init() {
        log.debug("initialize activemq id : {}", id);

        this.activeMQ = activeMQService.getEmbeddedActiveMQ();

        if (this.activeMQ != null)
            log.debug("Found activemq named : {}", activeMQ.getBrokerName());
        else
            this.activeMQ = new ActiveMQ();
    }

    public void save() throws IOException {
        log.debug("Saving activemq details {}", this.activeMQ.getId());

        this.activeMQService.updateEmbeddeedActiveMQ(this.activeMQ);

        this.redirect(pageUrl);
    }

    public void cancel() throws IOException {
        this.redirect(pageUrl);
    }
}
