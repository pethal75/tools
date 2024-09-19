package com.javaservices.tools.web.beans;

import com.javaservices.tools.controller.ApplicationsController;
import com.javaservices.tools.model.applications.Application;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.text.MessageFormat;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@EqualsAndHashCode(callSuper = true)
@Component
@ViewScoped
@Data
@Slf4j
public class ApplicationsDetailBean extends GenericPrimefacesBean {

    protected static final String pageUrl = "applicationDetail.xhtml";

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

        if (this.id == null)
            return;

        this.application = applicationsController.findApplicationById(id);

        // TODO error handling when not found application
        if (this.application == null)
            throw new IllegalArgumentException(MessageFormat.format("Application with id {0} not found!", this.id));

        log.debug("Found application named : {}", application.getName());
    }

    public List<Application> getApplications() {
        return applicationsController.getApplications();
    }

    public void save() {
        log.debug("Saving application details " + this.application.getId());

        // TODO Save configuration to database or disk in the future when loading/saving will be implemented
        /*String url = "applicationDetail.xhtml?name=" + this.application.getName();
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);*/
    }

    @Override
    protected String prepareUrl() {

        if (tabId == null)
            return MessageFormat.format("{0}?id={1}", pageUrl, this.id);
        else
            return MessageFormat.format("{0}?id={1}&tabId={2}", pageUrl, this.id, this.tabId);
    }
}
