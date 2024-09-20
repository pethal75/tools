package com.javaservices.tools.controller;

import com.javaservices.tools.dhl.DhlModel;
import com.javaservices.tools.model.ToolsModel;
import com.javaservices.tools.model.environments.Group;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GroupsController {

    // TODO read from session storage
    protected ToolsModel toolsModel = new DhlModel();

    public List<Group> getGroups() {
        return toolsModel.getGroups();
    }
}
