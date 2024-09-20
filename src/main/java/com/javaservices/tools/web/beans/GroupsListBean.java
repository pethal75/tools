package com.javaservices.tools.web.beans;

import com.javaservices.tools.controller.GroupsController;
import com.javaservices.tools.model.environments.Group;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
@ViewScoped
public class GroupsListBean {

    protected final GroupsController groupsController;

    @Inject
    public GroupsListBean(GroupsController groupsController) {
        this.groupsController = groupsController;
    }

    public List<Group> getGroups() {
        return groupsController.getGroups();
    }

}
