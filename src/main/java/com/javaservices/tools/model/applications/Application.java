package com.javaservices.tools.model.applications;

import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.servers.Server;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString(onlyExplicitlyIncluded = true)
public class Application {

    @ToString.Include
    protected Long id;

    @ToString.Include
    protected String name;

    protected String description;

    protected String notes;

    protected List<PropertyGroup> propertiesGroups;

    protected Server.Protocol protocol;

    protected Group group;

    protected List<ApplicationInstance> instances;

    public void initialize() {

        if (instances != null)
            instances.forEach(applicationInstance -> applicationInstance.initialize(this));

        if (propertiesGroups != null)
            propertiesGroups.forEach(PropertyGroup::initialize);
    }


    public List<Property> getProperties() {
        if (this.getPropertiesGroups() == null)
            return Collections.emptyList();

        return this.getPropertiesGroups().stream()
            .flatMap(propertyGroup -> propertyGroup.getProperties().stream())
            .collect(Collectors.toList());
    }
}
