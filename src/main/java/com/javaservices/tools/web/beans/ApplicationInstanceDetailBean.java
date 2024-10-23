package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.model.environments.Environment;
import com.javaservices.tools.service.ApplicationsService;
import com.javaservices.tools.service.ToolsModelService;
import static com.javaservices.tools.web.beans.ApplicationDetailBean.tabInstancesId;
import com.javaservices.tools.web.beans.primefaces.PrimefacesFormBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
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
public class ApplicationInstanceDetailBean extends PrimefacesFormBean<ApplicationInstance> implements Converter<Application> {

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
        return this.entity.getApplication() != null;
    }

    @Override
    public Application getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return applicationsService.findApplicationByName(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Application application) {
        if (application != null)
            return application.getName();

        return "Select One";
    }

    public void save() throws IOException {
        log.debug("Saving application instance details {}", this.entity.getId());

        this.applicationsService.updateApplicationInstance(this.entity);

        this.redirect(getBackUrl());
    }
}
