package com.jdocker.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.DockerClientBuilder;
import com.jdocker.model.ContainerInfo;
import com.jdocker.model.ImageInfo;

import java.util.ArrayList;
import java.util.List;

public class DockerService {

    private DockerClient dockerClient;

    public DockerService() {
        dockerClient = DockerClientBuilder.getInstance(
                "tcp://localhost:2375"
        ).build();
    }

    // Lister les images
    public List<ImageInfo> listImages() {
        List<Image> images = dockerClient.listImagesCmd().exec();
        List<ImageInfo> result = new ArrayList<>();

        for (Image img : images) {
            if (img.getRepoTags() != null) {
                for (String tag : img.getRepoTags()) {
                    String[] parts = tag.split(":");
                    result.add(new ImageInfo(
                            img.getId().substring(7, 19),
                            parts[0],
                            parts[1],
                            img.getSize()
                    ));
                }
            }
        }
        return result;
    }

    // Lister les conteneurs
    public List<ContainerInfo> listContainers() {
        List<Container> containers = dockerClient.listContainersCmd()
                .withShowAll(true)
                .exec();

        List<ContainerInfo> result = new ArrayList<>();

        for (Container c : containers) {
            result.add(new ContainerInfo(
                    c.getId().substring(0, 12),
                    c.getNames()[0].replace("/", ""),
                    c.getImage(),
                    c.getState(),
                    c.getStatus()
            ));
        }
        return result;
    }

    // Télécharger une image
    public void pullImage(String imageName) throws InterruptedException {
        dockerClient.pullImageCmd(imageName)
                .start()
                .awaitCompletion();
    }

    // Créer et démarrer un conteneur
    public String runContainer(String imageName) {
        return dockerClient.createContainerCmd(imageName)
                .exec()
                .getId();
    }

    // Démarrer un conteneur existant
    public void startContainer(String id) {
        dockerClient.startContainerCmd(id).exec();
    }

    // Stopper un conteneur
    public void stopContainer(String id) {
        dockerClient.stopContainerCmd(id).exec();
    }

    // Supprimer un conteneur
    public void removeContainer(String id) {
        dockerClient.removeContainerCmd(id).exec();
    }
}
