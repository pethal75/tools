package com.javaservices.tools.model.applications;

import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.servers.Server;
import com.javaservices.tools.web.beans.primefaces.EditableEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    protected List<ApplicationInstance> instances = new ArrayList<>();

    public void initialize() {

        if (instances == null)
            instances = new ArrayList<>();

        instances.forEach(applicationInstance -> applicationInstance.initialize(this));

        if (propertiesGroups != null) {
            propertiesGroups.forEach(propertyGroup -> propertyGroup.setApplication(this));
            propertiesGroups.forEach(PropertyGroup::initialize);
            propertiesGroups.sort(Comparator.comparing(PropertyGroup::getName));
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

    public Property findPropertyByName(String name) {
        if (this.propertiesGroups == null || name == null)
            return null;

        for(PropertyGroup group : this.propertiesGroups)
            if (group.findPropertyByName(name) != null)
                return group.findPropertyByName(name);

        return null;
    }

    public void addProperty(String groupName, Property property) {
        // Check whether property group exists
        PropertyGroup existingGroup = this.findPropertyGroupByName(groupName);

        if (existingGroup == null) {
            if (this.propertiesGroups == null)
                this.propertiesGroups = new ArrayList<>();

            Long maxId = this.propertiesGroups.stream()
                    .max(Comparator.comparingLong(PropertyGroup::getId))
                    .map(PropertyGroup::getId)
                    .orElse(1L);

            // Create property group
            existingGroup = PropertyGroup.builder().id(maxId + 1).name(groupName).build();

            this.propertiesGroups.add(existingGroup);
        }

        existingGroup.addProperty(property);
    }

    public void deletePropertyByName(String name) {
        if (this.propertiesGroups == null)
            return;

        this.propertiesGroups.forEach(propertyGroup -> propertyGroup.deletePropertyByName(name));
    }

    public PropertyGroup findPropertyGroupById(Long groupId) {
        if (this.propertiesGroups == null || groupId == null)
            return null;

        return this.propertiesGroups.stream()
                .filter(propertyGroup -> propertyGroup.getId().equals(groupId))
                .findFirst().orElse(null);
    }

    public PropertyGroup findPropertyGroupByName(String name) {
        if (this.propertiesGroups == null || name == null)
            return null;

        return this.propertiesGroups.stream()
                .filter(propertyGroup -> propertyGroup.getName().equals(name))
                .findFirst().orElse(null);
    }

    public void addInstance(ApplicationInstance applicationInstance) {
        if (this.instances == null)
            this.instances = new ArrayList<>();

        Long maxId = this.instances.stream()
                .map(ApplicationInstance::getId)
                .max(Long::compareTo)
                .orElse(1L);

        // Create property group
        applicationInstance.setId(maxId + 1);

        this.instances.add(applicationInstance);
        this.instances.sort(Comparator.comparing(ApplicationInstance::getName));
    }
}
