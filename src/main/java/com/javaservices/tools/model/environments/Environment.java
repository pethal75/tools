package com.javaservices.tools.model.environments;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Environment {

    protected Long id;

    protected String name;

    protected String description;
}
