package com.javaservices.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.netty.NettyDockerCmdExecFactory;
import com.github.dockerjava.okhttp.OkDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DockerManager {

    public static DockerClient dockerClient() {

        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .build();

        DockerHttpClient httpClient = new OkDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .build();

        return DockerClientBuilder.getInstance(config)
                .withDockerHttpClient(httpClient)
                .build();
    }

    public static List<Image> listImages() {
        List<Image> images = new ArrayList<>();

        // Create Docker client
        DockerClient dockerClient = dockerClient();

        try {
            // List Docker images
            images.addAll(dockerClient.listImagesCmd().exec());

            // Print Docker images
            for (Image image : images) {
                log.debug("Image ID: {}", image.getId());
                log.debug("Repo Tags: {}", String.join(", ", image.getRepoTags()));
                log.debug("Size: {}", image.getSize());
                log.debug("---------------------------------------------");
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            // Close resources
            try {
                dockerClient.close();
            } catch (IOException e) {
                // Just ignore
            }
        }

        return images;
    }

    /**
     * List all Docker containers on the host.
     *
     * @param showAll - If true, includes stopped containers in the list.
     * @return List of containers.
     */
    public static List<Container> listContainers(boolean showAll) {
        try {
            DockerClient client = dockerClient(); // Get the Docker client instance
            ListContainersCmd cmd = client.listContainersCmd().withShowAll(showAll);
            List<Container> containers = cmd.exec(); // Execute the command
            return containers;
        } catch (Exception e) {
            log.error("Error listing Docker containers", e);
            throw new RuntimeException("Failed to list Docker containers", e);
        }
    }
}
