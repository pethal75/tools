package com.javaservices.tools.service;

import com.javaservices.docker.DockerManager;
import com.javaservices.tools.model.docker.DockerImage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DockerService {

    public List<DockerImage> listImages() {
        return new ArrayList<>(DockerManager.listImages().stream()
                .map(image -> DockerImage.builder()
                        .imageId(image.getId())
                        .name(image.getLabels() != null ? String.join(", ", image.getLabels().values()): "")
                        .created(new Date(image.getCreated()).toString())
                        .size(image.getSize())
                        .tag(image.getRepoTags() != null ? String.join(", ", image.getRepoTags()): "")
                        .build()
                ).toList());
    }
}
