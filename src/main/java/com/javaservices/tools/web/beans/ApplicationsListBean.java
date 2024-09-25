package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.service.ApplicationsService;
import com.javaservices.tools.model.applications.Application;
import jakarta.el.MethodExpression;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ApplicationsListBean {

    public static final String pageUrl = "applicationsList.xhtml";
    public static final String pageUrlInstance = "instancesList.xhtml";

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
