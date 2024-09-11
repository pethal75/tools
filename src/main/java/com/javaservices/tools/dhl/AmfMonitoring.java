package com.javaservices.tools.dhl;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.servers.Server;
import java.util.Arrays;

public class AmfMonitoring {

    public static void setup(DhlModel model) {
        model.getGroups().add(Group.builder().name("AMF").build());

        model.getApplications().add(
                Application.builder()
                        .id(5L)
                        .name("AMF Monitoring")
                        .group(model.findGroup("AMF"))
                        .protocol(Server.Protocol.HTTP)
                        .instances(Arrays.asList(
                                ApplicationInstance.builder().environment(model.findEnvironment("LOCAL")).applicationUrl("").build()
                        ))
                        .build());


    }

}
