package com.javaservices.tools.model.environments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Environment {

    protected Long id;

    protected String name;

    protected String description;
}
