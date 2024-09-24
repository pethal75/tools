package com.javaservices.tools.web.beans;

import com.javaservices.tools.controller.EnvironmentsController;
import com.javaservices.tools.model.environments.Environment;
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
public class EnvironmentDetailBean extends PrimefacesBean {

    public static final String pageUrl = "environmentDetail.xhtml";

    protected EnvironmentsController environmentsController;

    @Value("#{request.getParameter('id')}")
    @ManagedProperty("id")
    protected Long id;

    protected Environment environment;

    @Inject
    public EnvironmentDetailBean(EnvironmentsController environmentsController) {
        this.environmentsController = environmentsController;
    }

    @PostConstruct
    public void init() {
        log.debug("initialize environment id : {}", id);

        this.environment = environmentsController.findEnvironmentById(id);

        if (this.environment != null)
            log.debug("Found environment named : {}", environment.getName());
        else
            this.environment = new Environment();
    }

    public void save() throws IOException {
        log.debug("Saving environment details {}", this.environment.getId());

        this.environmentsController.updateEnvironment(this.environment);

        this.redirect(EnvironmentsListBean.pageUrl);
    }

    public void cancel() throws IOException {
        this.redirect(EnvironmentsListBean.pageUrl);
    }
}
