package com.javaservices.tools.model.applications;

import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.servers.Server;
import com.javaservices.tools.web.beans.primefaces.EditableEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
@ToString(onlyExplicitlyIncluded = true)
public class Application implements EditableEntity {

    @ToString.Include
    protected Long id;

    @ToString.Include
    protected String name;

    protected String description;

    protected String notes;

    protected List<PropertyGroup> propertiesGroups = new ArrayList<>();

    protected Server.Protocol protocol;

    protected Group group;

    protected List<ApplicationInstance> instances;

    public void initialize() {

        if (instances != null)
            instances.forEach(applicationInstance -> applicationInstance.initialize(this));

        if (propertiesGroups != null) {
            propertiesGroups.forEach(propertyGroup -> propertyGroup.setApplication(this));
            propertiesGroups.forEach(PropertyGroup::initialize);
        }
    }

    @Override
    public EditableEntity clone() {
        return this.toBuilder().build();
    }

    public List<Property> getProperties() {
        if (this.propertiesGroups == null)
            return Collections.emptyList();

        return this.propertiesGroups.stream()
            .flatMap(propertyGroup -> propertyGroup.getProperties().stream())
            .collect(Collectors.toList());
    }

    public void deletePropertyByName(String name) {
        this.propertiesGroups.forEach(propertyGroup -> propertyGroup.deletePropertyByName(name));
    }

    public PropertyGroup findPropertyGroupById(Long groupId) {
        return this.propertiesGroups.stream().filter(propertyGroup -> propertyGroup.getId().equals(groupId)).findFirst().orElse(null);
    }
}
