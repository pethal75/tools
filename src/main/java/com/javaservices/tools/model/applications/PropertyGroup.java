package com.javaservices.tools.model.applications;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PropertyGroup {
    protected Long id;

    protected String name;

    protected List<Property> properties;

    public void initialize() {
        properties.forEach(property -> property.setGroup(this));
    }

    public void deletePropertyByName(String name) {
        getProperties().removeIf(property -> property.getName().equals(name));
    }
}
