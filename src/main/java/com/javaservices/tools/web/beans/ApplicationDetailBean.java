package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.Property;
import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.service.ApplicationsService;
import com.javaservices.tools.service.ToolsModelService;
import com.javaservices.tools.web.beans.primefaces.PrimefacesFormBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@ViewScoped
@Getter
@Setter
@Slf4j
public class ApplicationDetailBean extends PrimefacesFormBean<Application> {

    protected static final String pageUrl = "/pages/applications/applicationDetail.xhtml";
    protected static final Long tabPropertiesId = 1L;
    protected static final Long tabInstancesId = 2L;

    protected ApplicationsService applicationsService;

    protected ToolsModelService toolsModelService;

    @Inject
    public ApplicationDetailBean(ApplicationsService applicationsService, ToolsModelService toolsModelService) {
        this.applicationsService = applicationsService;
        this.toolsModelService = toolsModelService;
    }

    @PostConstruct
    public void init() {
        log.debug("initialize application id : {}", id);

        Application application = applicationsService.findApplicationById(id);

        if (application == null) {
            application = new Application();
        }

        super.initialize(application);
    }

    @Override
    public String getUrl() {
        return pageUrl;
    }

    @Override
    public String getBackUrl() {
        return ApplicationsListBean.pageUrlApplications;
    }

    @Override
    protected String prepareUrl() {

        if (tabId == null)
            return MessageFormat.format("{0}?id={1}", pageUrl, this.id);
        else
            return MessageFormat.format("{0}?id={1}&tabId={2}", pageUrl, this.id, this.tabId);
    }

    public List<Application> getApplications() {
        return applicationsService.getApplications();
    }

    public List<Group> getGroups() {
        return this.toolsModelService.getModel().getGroups();
    }

    public List<Property> getProperties() {
        return this.entity.getProperties();
    }

    public void deletePropertyByName(String name) {
        this.applicationsService.deletePropertyByName(entity, name);
    }

    /**
     * Saves the current application details to the database.
     *
     * This method logs the saving action, updates the application entity using the
     * applicationsService, and redirects to the applications list page. It throws
     * an IOException if redirection fails.
     *
     * @throws IOException if an input or output exception occurs during the redirection.
     */
    public void save() throws IOException {
        log.debug("Saving application details {}", entity.getId());

        this.applicationsService.updateApplication(entity);

        this.redirect(ApplicationsListBean.pageUrlApplications);
    }

}
