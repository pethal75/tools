package com.javaservices.tools.service;

import com.javaservices.docker.DockerManager;
import com.javaservices.tools.model.docker.DockerContainer;
import com.javaservices.tools.model.docker.DockerImage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DockerService {

    public List<DockerImage> listImages() {
        return new ArrayList<>(DockerManager.listImages().stream()
                .map(image -> DockerImage.mapImage(image)
                ).toList());
    }
    public List<DockerContainer> listContainers() {
        return new ArrayList<>(DockerManager.listContainers(true).stream()
                .map(container -> DockerContainer.mapContainer(container)
                ).toList());
    }
}
