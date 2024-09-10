package com.javaservices.tools.model.applications;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Property {
    protected String name;
    protected String value;

    public enum PropertyType {
        NUMBER, TEXT, URL
    }

    protected PropertyType type;
}
