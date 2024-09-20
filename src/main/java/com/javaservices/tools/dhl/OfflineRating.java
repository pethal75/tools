package com.javaservices.tools.dhl;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.servers.Server;
import java.util.Arrays;

public class OfflineRating {

    public static void setup(DhlModel model) {
        model.getGroups().add(Group.builder().id(3L).name("OFR").description("Offline Rating").build());

        model.getApplications().add(
                Application.builder()
                        .id(10L)
                        .name("OfflineRating")
                        .group(model.findGroup("OFR"))
                        .protocol(Server.Protocol.HTTP)
                        .instances(Arrays.asList(
                                ApplicationInstance.builder().environment(model.findEnvironment("LOCAL")).applicationUrl("http://localhost:1601/kyc-enc/actuator/health").build()
                        ))
                        .build());


    }
}
