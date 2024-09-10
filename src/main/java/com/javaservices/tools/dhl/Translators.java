package com.javaservices.tools.dhl;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.servers.Server;
import java.util.Arrays;

public class Translators {

    public static void setup(DhlModel model) {
        model.getGroups().add(Group.builder().name("Translators").build());

        model.getApplications().add(
                Application.builder()
                        .id(21)
                        .name("Translator KYC")
                        .group(model.findGroup("Translators"))
                        .protocol(Server.Protocol.HTTP)
                        .instances(Arrays.asList(
                                ApplicationInstance.builder().environment(model.findEnvironment("LOCAL")).applicationUrl("").build()
                        ))
                        .build());

        model.getApplications().add(
                Application.builder()
                        .id(22)
                        .name("Translator KYC Amazon")
                        .group(model.findGroup("Translators"))
                        .protocol(Server.Protocol.HTTP)
                        .instances(Arrays.asList(
                                ApplicationInstance.builder().environment(model.findEnvironment("LOCAL")).applicationUrl("").build()
                        ))
                        .build());

        model.getApplications().add(
                Application.builder()
                        .id(23)
                        .name("Translator PMF")
                        .group(model.findGroup("Translators"))
                        .protocol(Server.Protocol.HTTP)
                        .instances(Arrays.asList(
                                ApplicationInstance.builder().environment(model.findEnvironment("LOCAL")).applicationUrl("").build()
                        ))
                        .build());


    }
}
