package com.javaservices.tools.service;

import com.javaservices.tools.model.messaging.ActiveMQ;
import com.javaservices.tools.model.servers.Server;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActiveMQService {

    private final ToolsModelService toolsModelService;

    public ActiveMQService(ToolsModelService toolsModelService) {
        this.toolsModelService = toolsModelService;
    }

    public ActiveMQ getEmbeddedActiveMQ() {
        return toolsModelService.getModel().getEmbeddedActiveMQ();
    }

    public void updateEmbeddeedActiveMQ(ActiveMQ activeMQ) {
        this.toolsModelService.getModel().setEmbeddedActiveMQ(activeMQ);

        this.toolsModelService.saveModel();
    }

    public void startBroker() {

    }

    public void stopBroker() {

    }
}
