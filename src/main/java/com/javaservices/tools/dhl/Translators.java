package com.javaservices.tools.dhl;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.servers.Server;
import java.util.Arrays;

public class Translators {

    public static void setup(DhlModel model) {
        model.getGroups().add(Group.builder().id(4L).name("Translators").description("Translators applications").build());

        model.getApplications().add(
                Application.builder()
                        .id(21L)
                        .name("Translator KYC")
                        .group(model.findGroupByName("Translators"))
                        .protocol(Server.Protocol.HTTP)
                        .instances(Arrays.asList(
                                ApplicationInstance.builder().environment(model.findEnvironmentByName("LOCAL")).applicationUrl("").build()
                        ))
                        .build());

        model.getApplications().add(
                Application.builder()
                        .id(22L)
                        .name("Translator KYC Amazon")
                        .group(model.findGroupByName("Translators"))
                        .protocol(Server.Protocol.HTTP)
                        .instances(Arrays.asList(
                                ApplicationInstance.builder().environment(model.findEnvironmentByName("LOCAL")).applicationUrl("").build()
                        ))
                        .build());

        model.getApplications().add(
                Application.builder()
                        .id(23L)
                        .name("Translator PMF")
                        .group(model.findGroupByName("Translators"))
                        .protocol(Server.Protocol.HTTP)
                        .instances(Arrays.asList(
                                ApplicationInstance.builder().environment(model.findEnvironmentByName("LOCAL")).applicationUrl("").build()
                        ))
                        .build());


    }
}
