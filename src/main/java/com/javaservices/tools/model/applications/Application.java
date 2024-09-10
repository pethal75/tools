package com.javaservices.tools.model.applications;

import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.servers.Server;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString(onlyExplicitlyIncluded = true)
public class Application {

    @ToString.Include
    protected String name;

    @ToString.Include
    protected String description;

    @ToString.Include
    protected String notes;

    protected Server.Protocol protocol;

    @ToString.Include
    protected Group group;

    protected List<ApplicationInstance> instances;

    public void initialize() {
        instances.forEach(applicationInstanceConfiguration -> applicationInstanceConfiguration.setApplication(this));
    }


}
