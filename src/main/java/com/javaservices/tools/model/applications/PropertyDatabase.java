package com.javaservices.tools.model.applications;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Value
@SuperBuilder
@ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class PropertyDatabase extends Property {

    protected String connection;

    protected String username;

    protected String password;
}
