package com.javaservices.tools.web.beans;

import com.javaservices.tools.controller.ApplicationsController;
import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.sun.javafx.binding.StringFormatter;
import jakarta.annotation.PostConstruct;
import jakarta.el.MethodExpression;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.annotation.View;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.text.MessageFormat;
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

    @Value("#{request.getParameter('id')}")
    @ManagedProperty("id")
    protected Long id;

    protected Application application;

    @Inject
    public ApplicationsDetailBean(ApplicationsController applicationsController) {
        this.applicationsController = applicationsController;
    }

    @PostConstruct
    public void init() {
        log.debug("initialize application id : {}", id);

        this.application = applicationsController.findApplicationById(id);

        // TODO error handling when not found application
        if (this.application == null)
            throw new IllegalArgumentException(MessageFormat.format("Application with id {} not found!", this.id));

        log.debug("Found application named : {}", application.getName());
    }

    public List<Application> getApplications() {
        return applicationsController.getApplications();
    }

    public List<ApplicationInstance> getApplicationInstances() {
        return applicationsController.getApplicationInstances();
    }

    public void save() throws IOException {
        log.debug("Saving application details " + this.application.getId());

        /*String url = "applicationDetail.xhtml?name=" + this.application.getName();
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);*/
    }
}
