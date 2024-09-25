package com.javaservices.tools.service;

import com.javaservices.tools.model.environments.Environment;
import com.javaservices.tools.model.environments.Group;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GroupsService {

    private final ModelService modelService;

    public GroupsService(ModelService modelService) {
        this.modelService = modelService;
    }

    public List<Group> getGroups() {
        return modelService.getModel().getGroups();
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
            Long maxId = this.modelService.getModel().getGroups().stream()
                    .max(Comparator.comparingLong(Group::getId))
                    .map(Group::getId)
                    .orElse(0L);
            group.setId(maxId + 1);

            this.modelService.getModel().getGroups().add(group);
        } else {

        }
    }

    public void delete(Long id) {
        Group group = findGroupById(id);

        if (group != null) {
            this.modelService.getModel().getGroups().remove(group);
        }
    }
}
