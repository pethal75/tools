package com.javaservices.tools.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ToolsCustomProperties {
    public static final String STORAGE_TYPE_FILE = "FILE";

    public String storageType;

    public String storageDirectory;

    public String lastProject;
}
