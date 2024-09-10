package com.javaservices.tools.model.utilities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tomcat {

    private String tomcatUrl;

    private String tomcatFolder;

}
