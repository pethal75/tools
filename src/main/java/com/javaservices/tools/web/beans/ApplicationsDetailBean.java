package com.javaservices.tools.web.beans;

import com.javaservices.tools.controller.ApplicationsController;
import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.annotation.View;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@ViewScoped
@Data
@Slf4j
public class ApplicationsDetailBean {

    protected ApplicationsController applicationsController;

    @Value("#{request.getParameter('name')}")
    @ManagedProperty("name")
    protected String name;

    protected Application application;

    @Inject
    public ApplicationsDetailBean(ApplicationsController applicationsController) {
        this.applicationsController = applicationsController;
    }

    @PostConstruct
    public void init() {
        log.debug("initialize application " + name);

        this.application = applicationsController.findApplicationByName(name);
    }

    public List<Application> getApplications() {
        return applicationsController.getApplications();
    }

    public List<ApplicationInstance> getApplicationInstances() {
        return applicationsController.getApplicationInstances();
    }
}
