package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.service.ApplicationsService;
import com.javaservices.tools.web.beans.primefaces.PrimefacesTabBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.IOException;
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
public class ApplicationInstanceDetailBean extends PrimefacesTabBean {

    protected static final String pageUrl = "applicationInstanceDetail.xhtml";

    protected ApplicationsService applicationsService;

    @Value("#{request.getParameter('id')}")
    @ManagedProperty("id")
    protected Long id;

    protected ApplicationInstance applicationInstance;

    @Inject
    public ApplicationInstanceDetailBean(ApplicationsService applicationsService) {
        this.applicationsService = applicationsService;
    }

    @PostConstruct
    public void init() {
        log.debug("initialize application instance id : {}", id);

        this.applicationInstance = applicationsService.findApplicationInstanceById(id);

        if (this.applicationInstance != null)
            log.debug("Found application instance named : {}", applicationInstance.getName());
        else
            this.applicationInstance = new ApplicationInstance();
    }

    public List<Application> getApplications() {
        return applicationsService.getApplications();
    }

    public void save() throws IOException {
        log.debug("Saving application instance details {}", this.applicationInstance.getId());

        this.applicationsService.updateApplicationInstance(this.applicationInstance);

        this.redirect(ApplicationsListBean.pageUrlInstance);
    }

    public void cancel() throws IOException {
        this.redirect(ApplicationsListBean.pageUrlInstance);
    }

}
