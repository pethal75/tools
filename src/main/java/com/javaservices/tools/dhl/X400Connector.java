package com.javaservices.tools.dhl;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.servers.Server;
import java.util.Arrays;

public class X400Connector {

    public static void setup(DhlModel model) {
        model.getGroups().add(Group.builder().id(5L).name("X400").description("X400 Connector").build());

        model.getApplications().add(
                Application.builder()
                        .id(40L)
                        .name("X400 Connector")
                        .group(model.findGroupByName("X400"))
                        .protocol(Server.Protocol.HTTP)
                        .instances(Arrays.asList(
                                ApplicationInstance.builder().environment(model.findEnvironmentByName("LOCAL")).applicationUrl("").build()
                        ))
                        .build());


    }
}
