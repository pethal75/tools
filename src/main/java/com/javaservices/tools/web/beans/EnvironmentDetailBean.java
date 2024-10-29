package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.environments.Environment;
import com.javaservices.tools.service.EnvironmentsService;
import com.javaservices.tools.web.beans.primefaces.PrimefacesBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.IOException;
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
public class EnvironmentDetailBean extends PrimefacesBean {

    public static final String pageUrl = "environmentDetail.xhtml";

    protected EnvironmentsService environmentsService;

    @Value("#{request.getParameter('id')}")
    @ManagedProperty("id")
    protected Long id;

    protected Environment environment;

    @Inject
    public EnvironmentDetailBean(EnvironmentsService environmentsService) {
        this.environmentsService = environmentsService;
    }

    @PostConstruct
    public void init() {
        log.debug("initialize environment id : {}", id);

        this.environment = environmentsService.findEnvironmentById(id);

        if (this.environment != null)
            log.debug("Found environment named : {}", environment.getName());
        else
            this.environment = new Environment();
    }

    public void save() throws IOException {
        log.debug("Saving environment details {}", this.environment.getId());

        this.environmentsService.updateEnvironment(this.environment);

        this.redirect(EnvironmentsListBean.pageUrl);
    }

    public void cancel() throws IOException {
        this.redirect(EnvironmentsListBean.pageUrl);
    }
}
