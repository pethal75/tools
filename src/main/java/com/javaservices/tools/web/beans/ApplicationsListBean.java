package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.controller.ApplicationsController;
import com.javaservices.tools.model.applications.Application;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ApplicationsListBean {

    protected ApplicationsController applicationsController;

    public ApplicationsListBean(ApplicationsController applicationsController) {
        this.applicationsController = applicationsController;
    }

    public List<Application> getApplications() {
        return applicationsController.getApplications();
    }

    public List<ApplicationInstance> getApplicationInstances() {
        return applicationsController.getApplicationInstances();
    }
}
