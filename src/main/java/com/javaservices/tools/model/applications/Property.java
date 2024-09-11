package com.javaservices.tools.model.applications;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString(onlyExplicitlyIncluded = true)
public class Property {

    @ToString.Include
    protected String name;

    @ToString.Include
    protected String value;

    public enum PropertyType {
        NUMBER, TEXT, URL
    }

    @ToString.Include
    protected PropertyType type;

    protected PropertyGroup group;
}
