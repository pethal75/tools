package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.environments.Environment;
import com.javaservices.tools.service.EnvironmentsService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
@ViewScoped
public class DockerListBean {

    public static final String pageUrl = "dockerList.xhtml";

    protected final EnvironmentsService environmentsService;

    @Inject
    public DockerListBean(EnvironmentsService environmentsService) {
        this.environmentsService = environmentsService;
    }

    public List<Environment> getEnvironments() {
        return environmentsService.getEnvironments();
    }

    public void deleteEnvironment(Long id) {
        this.environmentsService.delete(id);
    }
}
