package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.docker.DockerContainer;
import com.javaservices.tools.model.docker.DockerImage;
import com.javaservices.tools.model.environments.Environment;
import com.javaservices.tools.service.DockerService;
import com.javaservices.tools.service.EnvironmentsService;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
@ViewScoped
public class DockerListBean {

    public static final String pageUrl = "dockerList.xhtml";

    protected final DockerService dockerService;

    @Inject
    public DockerListBean(DockerService dockerService) {
        this.dockerService = dockerService;
    }

    public List<DockerImage> getImages() {
        return dockerService.listImages();
    }

    public List<DockerContainer> getContainers() {
        return dockerService.listContainers();
    }
}
