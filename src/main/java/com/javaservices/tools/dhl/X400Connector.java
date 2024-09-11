package com.javaservices.tools.dhl;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.servers.Server;
import java.util.Arrays;

public class X400Connector {

    public static void setup(DhlModel model) {
        model.getGroups().add(Group.builder().name("X400").build());

        model.getApplications().add(
                Application.builder()
                        .id(40L)
                        .name("X400 Connector")
                        .group(model.findGroup("X400"))
                        .protocol(Server.Protocol.HTTP)
                        .instances(Arrays.asList(
                                ApplicationInstance.builder().environment(model.findEnvironment("LOCAL")).applicationUrl("").build()
                        ))
                        .build());


    }
}
