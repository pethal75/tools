package com.javaservices.tools.model.applications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Property {

    @ToString.Include
    protected String name;

    @ToString.Include
    protected String value;

    public enum PropertyType {
        NUMBER, TEXT, URL, DATABASE
    }

    @ToString.Include
    protected PropertyType type;

    protected PropertyGroup group;
}
