
package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.messaging.ActiveMQ;
import com.javaservices.tools.service.ActiveMQService;
import com.javaservices.tools.web.beans.primefaces.PrimefacesFormBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = false)
@Component
@ViewScoped
@Data
@Slf4j
public class ActiveMQDetailBean extends PrimefacesFormBean<ActiveMQ> {

    public static final String pageUrl = "activemqDetail.xhtml";

    protected ActiveMQService activeMQService;

    @Inject
    public ActiveMQDetailBean(ActiveMQService activeMQService) {
        this.activeMQService = activeMQService;
    }

    @PostConstruct
    public void init() {
        if (id != null)
            log.debug("initialize activemq id : {}", id);


        if (activeMQService.getEmbeddedActiveMQ() != null) {
            log.debug("Found activemq named : {}", entity.getBrokerName());

            super.initialize(activeMQService.getEmbeddedActiveMQ());
        } else {
            super.initialize(new ActiveMQ());
        }
    }

    public void save() throws Exception {
        log.debug("Saving activemq details {}", entity.getId());

        activeMQService.updateEmbeddeedActiveMQ(entity);

        this.redirect(pageUrl);
    }

    public String getUrl() {
        return pageUrl;
    }

    public String getBackUrl() {
        return "/index.xhtml";
    }
}
