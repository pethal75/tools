package com.javaservices.tools.web.beans;

import com.javaservices.tools.controller.EnvironmentsController;
import com.javaservices.tools.model.environments.Environment;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
@ViewScoped
public class EnvironmentsListBean {

    protected final EnvironmentsController environmentsController;

    @Inject
    public EnvironmentsListBean(EnvironmentsController environmentsController) {
        this.environmentsController = environmentsController;
    }

    public List<Environment> getEnvironments() {
        return environmentsController.getEnvironments();
    }

}
