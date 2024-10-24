package com.javaservices.tools.dhl;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.servers.Server;
import java.util.Arrays;

public class AmfMonitoring {

    public static void setup(DhlModel model) {
        model.getGroups().add(Group.builder().id(2L).name("AMF").description("Application monitoring").build());

        model.getApplications().add(
                Application.builder()
                        .id(5L)
                        .name("AMF Monitoring")
                        .group(model.findGroupByName("AMF"))
                        .protocol(Server.Protocol.HTTP)
                        .instances(Arrays.asList(
                                ApplicationInstance.builder().environment(model.findEnvironmentByName("LOCAL")).applicationUrl("").build()
                        ))
                        .build());


    }

}
