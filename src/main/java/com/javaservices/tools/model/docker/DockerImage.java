package com.javaservices.tools.model.docker;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DockerImage {
    private String name;
    private String tag;
    private String imageId;
    private String created;
    private long size;
}
