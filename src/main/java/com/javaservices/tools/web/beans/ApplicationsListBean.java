package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.service.ApplicationsService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ApplicationsListBean {

    public static final String pageUrlApplications = "/pages/applications/applicationsList.xhtml";
    public static final String pageUrlInstances = "/pages/applications/instancesList.xhtml";

    protected final ApplicationsService applicationsService;

    public ApplicationsListBean(ApplicationsService applicationsService) {
        this.applicationsService = applicationsService;
    }

    public List<Application> getApplications() {
        return applicationsService.getApplications();
    }

    public List<ApplicationInstance> getApplicationInstances() {
        return applicationsService.getApplicationInstances();
    }

    public void deleteApplication(Long id) {
        this.applicationsService.delete(id);
    }
}
