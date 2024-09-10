package com.javaservices.tools.dhl;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.servers.Server;
import java.util.Arrays;

public class UIApplications {

    public static void setup(DhlModel model) {
        model.getGroups().add(Group.builder().name("UI").build());

        model.getApplications().add(
                Application.builder()
                        .name("Main UI")
                        .group(model.findGroup("UI"))
                        .protocol(Server.Protocol.HTTP)
                        .instances(Arrays.asList(
                                ApplicationInstance.builder().environment(model.findEnvironment("DEV")).applicationUrl("https://exp-cfit-dev-fwui.test-int.apps.czchooct002.dhl.com").build(),
                                ApplicationInstance.builder().environment(model.findEnvironment("LOCAL")).applicationUrl("http://localhost:2020/pages/public/login.xhtml").build()
                        ))
                        .build());

        model.getApplications().add(
                Application.builder()
                        .name("Dashboard UI")
                        .group(model.findGroup("UI"))
                        .protocol(Server.Protocol.HTTP)
                        .instances(Arrays.asList(
                                ApplicationInstance.builder().environment(model.findEnvironment("DEV")).applicationUrl("https://exp-cfit-dev-fwds.test-int.apps.czchooct002.dhl.com/dccDashboard").build(),
                                ApplicationInstance.builder().environment(model.findEnvironment("LOCAL")).applicationUrl("http://localhost:2020/dccDashboard/pages/public/login.xhtml").build()
                        ))
                        .build());


    }
}
