package com.javaservices.tools.model.docker;

import lombok.Data;

@Data
public class DockerContainer {
    private String containerId;    // CONTAINER ID
    private String imageName;      // IMAGE NAME
    private String command;        // COMMAND
    private String created;        // CREATED
    private String status;         // STATUS
    private String ports;          // PORTS
    private String names;          // NAMES
}
