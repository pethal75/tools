package com.javaservices.tools.web.beans;

import com.javaservices.tools.controller.GroupsController;
import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.servers.Server;
import com.javaservices.tools.web.beans.primefaces.PrimefacesBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@EqualsAndHashCode
@Component
@ViewScoped
@Data
@Slf4j
public class GroupDetailBean extends PrimefacesBean {

    public static final String pageUrl = "groupDetail.xhtml";

    protected GroupsController groupsController;

    @Value("#{request.getParameter('id')}")
    @ManagedProperty("id")
    protected Long id;

    protected Group group;

    @Inject
    public GroupDetailBean(GroupsController groupsController) {
        this.groupsController = groupsController;
    }

    @PostConstruct
    public void init() {
        log.debug("initialize group id : {}", id);

        this.group = groupsController.findGroupById(id);

        if (this.group != null)
            log.debug("Found group named : {}", group.getName());
        else
            this.group = new Group();
    }

    public void save() throws IOException {
        log.debug("Saving group details {}", this.group.getId());

        this.groupsController.updateGroup(this.group);

        this.redirect(GroupsListBean.pageUrl);
    }

    public void cancel() throws IOException {
        this.redirect(GroupsListBean.pageUrl);
    }

}
