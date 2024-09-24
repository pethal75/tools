package com.javaservices.tools.controller;

import com.javaservices.tools.dhl.DhlModel;
import com.javaservices.tools.model.ToolsModel;
import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.servers.Server;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GroupsController {

    private final ModelController modelController;

    public GroupsController(ModelController modelController) {
        this.modelController = modelController;
    }

    public List<Group> getGroups() {
        return modelController.getModel().getGroups();
    }

    public Group findGroupById(Long id) {
        return this.getGroups().stream().filter(group -> Objects.equals(group.getId(), id)).findFirst().orElse(null);
    }

    public void updateGroup(Group group) {
        // TODO save group according saving method - file / database

        if (group.getId() == null) {
            Long maxId = this.modelController.getModel().getGroups().stream()
                    .max(Comparator.comparingLong(Group::getId))
                    .map(Group::getId)
                    .orElse(0L);
            group.setId(maxId + 1);

            this.modelController.getModel().getGroups().add(group);
        } else {

        }
    }
}
