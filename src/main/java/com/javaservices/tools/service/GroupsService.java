package com.javaservices.tools.service;

import com.javaservices.tools.model.environments.Group;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GroupsService {

    private final ToolsModelService toolsModelService;

    public GroupsService(ToolsModelService toolsModelService) {
        this.toolsModelService = toolsModelService;
    }

    public List<Group> getGroups() {
        return toolsModelService.getModel().getGroups();
    }

    public Group findGroupById(Long id) {
        if (this.getGroups() == null)
            return null;

        return this.getGroups().stream()
                .filter(group -> Objects.equals(group.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public void updateGroup(Group group) {
        // TODO save group according saving method - file / database

        if (group.getId() == null) {
            Long maxId = this.toolsModelService.getModel().getGroups().stream()
                    .max(Comparator.comparingLong(Group::getId))
                    .map(Group::getId)
                    .orElse(0L);
            group.setId(maxId + 1);

            this.toolsModelService.getModel().getGroups().add(group);
        } else {

        }
    }

    public void delete(Long id) {
        Group group = findGroupById(id);

        if (group != null) {
            this.toolsModelService.getModel().getGroups().remove(group);
        }
    }
}
