package com.javaservices.tools.model.applications;

import java.util.ArrayList;
import java.util.Comparator;
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

    protected List<Property> properties;

    public void initialize() {
        if (properties == null)
            return;

        properties.forEach(property -> property.setGroup(this));
        properties.sort(Comparator.comparing(Property::getName));
    }

    public void deletePropertyByName(String name) {
        properties.removeIf(property -> property.getName().equals(name));
    }

    public Property findPropertyByName(String name) {
        if (properties == null || name == null)
            return null;

        return properties.stream()
                .filter(property -> property.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public void addProperty(Property property) {
        if (properties == null)
            properties = new ArrayList<>();

        properties.add(property);

        properties.sort(Comparator.comparing(Property::getName));
    }
}
