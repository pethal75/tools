package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.service.ApplicationsService;
import com.javaservices.tools.web.beans.primefaces.PrimefacesFormBean;
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
public class ApplicationDetailBean extends PrimefacesFormBean<Application> {

    protected static final String pageUrl = "/pages/applications/applicationDetail.xhtml";

    protected ApplicationsService applicationsService;

    @Value("#{request.getParameter('id')}")
    @ManagedProperty("id")
    protected Long id;

    Application application;

    @Inject
    public ApplicationDetailBean(ApplicationsService applicationsService) {
        this.applicationsService = applicationsService;
    }

    @PostConstruct
    public void init() {
        log.debug("initialize application id : {}", id);

        application = applicationsService.findApplicationById(id);

        if (application != null) {
            log.debug("Found application named : {}", application.getName());

            super.initialize(application);
        } else {
            super.initialize(new Application());
        }
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

    @Override
    public String getUrl() {
        return pageUrl;
    }

    @Override
    public String getBackUrl() {
        return ApplicationsListBean.pageUrlApplications;
    }

    public void save() throws IOException {
        log.debug("Saving application details {}", entity.getId());

        this.applicationsService.updateApplication(entity);

        this.redirect(ApplicationsListBean.pageUrlApplications);
    }

    public void deletePropertyByName(String name) {
        this.applicationsService.deletePropertyByName(entity, name);
    }

}
