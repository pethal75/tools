package com.javaservices.tools.web.beans;

import com.javaservices.tools.service.GroupsService;
import com.javaservices.tools.model.environments.Group;
import jakarta.el.MethodExpression;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
@ViewScoped
public class GroupsListBean {

    public static final String pageUrl = "groupsList.xhtml";

    protected final GroupsService groupsService;

    @Inject
    public GroupsListBean(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    public List<Group> getGroups() {
        return groupsService.getGroups();
    }


    public void deleteGroup(Long id) {
        this.groupsService.delete(id);
    }
}
