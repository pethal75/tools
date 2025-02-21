package com.javaservices.tools.model.docker;

import com.github.dockerjava.api.model.Image;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class DockerImage {
    private String name;
    private String tag;
    private String version;
    private String imageId;
    private String created;
    private long size;

    public static DockerImage mapImage(Image image) {
        String name = "";
        String tag = "";
        String version = "";


        if (image.getRepoTags() != null && image.getRepoTags().length > 0) {
            String repoTags = image.getRepoTags()[0];

            if (repoTags.contains("/"))
                name = repoTags.substring(0, repoTags.indexOf("/"));
            else if (repoTags.contains(":"))
                name = repoTags.substring(0, repoTags.indexOf(":"));
            else
                name = repoTags;

            if (repoTags.contains(":"))
                version = repoTags.substring(repoTags.indexOf(":") + 1);

            if (repoTags.contains(":") && repoTags.contains("/"))
                tag = repoTags.substring(repoTags.indexOf("/") + 1,repoTags.indexOf(":"));
        }

        return DockerImage.builder()
                .name(name)
                .imageId(image.getId())
                .created(new Date(image.getCreated()).toString())
                .size(image.getSize())
                .tag(tag)
                .version(version)
                .build();
    }
}
