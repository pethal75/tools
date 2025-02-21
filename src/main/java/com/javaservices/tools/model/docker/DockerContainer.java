package com.javaservices.tools.model.docker;

import com.github.dockerjava.api.model.Container;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;

@Data
@Builder
public class DockerContainer {
    private String containerId;    // CONTAINER ID
    private String imageName;      // IMAGE NAME
    private String command;        // COMMAND
    private String created;        // CREATED
    private String status;         // STATUS
    private String ports;          // PORTS
    private String names;          // NAMES

    public static DockerContainer mapContainer(Container container) {
        return DockerContainer.builder()
                .containerId(container.getId())
                .imageName(container.getImage())
                .command(container.getCommand())
                .created(new Date(container.getCreated()).toString())
                .status(container.getStatus())
                .ports(Arrays.toString(container.getPorts()))
                .names(Arrays.toString(container.getNames()))
                .build();
    }
}
