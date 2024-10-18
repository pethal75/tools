package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.service.ApplicationsService;
import com.javaservices.tools.web.beans.primefaces.PrimefacesTabBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.IOException;
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
public class ApplicationDetailBean extends PrimefacesTabBean {

    protected static final String pageUrl = "/pages/applications/applicationDetail.xhtml";

    protected ApplicationsService applicationsService;

    @Value("#{request.getParameter('id')}")
    @ManagedProperty("id")
    protected Long id;

    protected Application application;

    @Inject
    public ApplicationDetailBean(ApplicationsService applicationsService) {
        this.applicationsService = applicationsService;
    }

    @PostConstruct
    public void init() {
        log.debug("initialize application id : {}", id);

        this.application = applicationsService.findApplicationById(id);

        if (this.application != null)
            log.debug("Found application named : {}", application.getName());
        else
            this.application = new Application();
    }

    public List<Application> getApplications() {
        return applicationsService.getApplications();
    }

    @Override
    protected String prepareUrl() {

        if (tabId == null)
            return MessageFormat.format("{0}?id={1}", pageUrl, this.id);
        else
            return MessageFormat.format("{0}?id={1}&tabId={2}", pageUrl, this.id, this.tabId);
    }

    public void save() throws IOException {
        log.debug("Saving application details {}", this.application.getId());

        this.applicationsService.updateApplication(this.application);

        this.redirect(ApplicationsListBean.pageUrlApplications);
    }

    public void cancel() throws IOException {
        this.redirect(ApplicationsListBean.pageUrlApplications);
    }

    public void saveModel() {
        // TODO
    }
}
