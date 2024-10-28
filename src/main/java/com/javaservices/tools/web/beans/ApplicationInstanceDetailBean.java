package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.model.environments.Environment;
import com.javaservices.tools.model.servers.Server;
import com.javaservices.tools.service.ApplicationsService;
import com.javaservices.tools.service.ToolsModelService;
import static com.javaservices.tools.web.beans.ApplicationDetailBean.tabInstancesId;
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
public class ApplicationInstanceDetailBean extends PrimefacesFormBean<ApplicationInstance> {

    protected static final String pageUrl = "applicationInstanceDetail.xhtml";

    protected ApplicationsService applicationsService;

    protected ToolsModelService toolsModelService;

    protected Application application;

    @Value("#{request.getParameter('applicationId')}")
    @ManagedProperty("applicationId")
    protected Long applicationId;

    @Inject
    public ApplicationInstanceDetailBean(ApplicationsService applicationsService,ToolsModelService toolsModelService) {
        this.applicationsService = applicationsService;
        this.toolsModelService = toolsModelService;
    }

    @PostConstruct
    public void init() {
        log.debug("initialize application instance id : {} for application id {}", id, applicationId);

        this.application = applicationsService.findApplicationById(applicationId);

        ApplicationInstance existingInstance = applicationsService.findApplicationInstanceById(id);

        if (existingInstance == null)
            existingInstance = new ApplicationInstance();

        existingInstance.setApplication(application);

        super.initialize(existingInstance);
    }

    public List<Application> getApplications() {
        return applicationsService.getApplications();
    }

    public List<Environment> getEnvironments() {
        return toolsModelService.getModel().getEnvironments();
    }

    @Override
    public String getUrl() {
        return pageUrl;
    }

    @Override
    public String getBackUrl() {
        return ApplicationDetailBean.pageUrl + MessageFormat.format("?id={0}&tabId={1}", application.getId(), tabInstancesId);
    }

    public boolean isExistingInstance() {
        return this.origEntity.getApplication() != null;
    }

    public void save() throws IOException {
        log.debug("Saving application instance details {}", this.entity.getId());

        this.applicationsService.updateApplicationInstance(this.entity);

        this.redirect(getBackUrl());
    }

    public List<Server> getServers() {
        return this.toolsModelService.getModel().getServers();
    }
}
