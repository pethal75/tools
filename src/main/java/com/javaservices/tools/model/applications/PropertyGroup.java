package com.javaservices.tools.model.applications;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder(toBuilder = true)
@ToString(onlyExplicitlyIncluded = true)
public class PropertyGroup {

    @ToString.Include
    protected Long id;

    @ToString.Include
    protected String name;

    protected Application application;

    protected List<Property> properties = new ArrayList<>();

    public void initialize() {
        properties.forEach(property -> property.setGroup(this));
    }

    public void deletePropertyByName(String name) {
        properties.removeIf(property -> property.getName().equals(name));
    }

    public Property findPropertyByName(String name) {
        return properties.stream().filter(property -> property.getName().equals(name)).findFirst().orElse(null);
    }

}
