package com.javaservices.tools.dhl;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.model.applications.Property;
import com.javaservices.tools.model.applications.PropertyGroup;
import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.servers.Server;
import java.util.Arrays;

public class UIApplications {

    public static void setup(DhlModel model) {
        model.getGroups().add(Group.builder().name("UI").build());

        model.getApplications().add(
                Application.builder()
                        .id(1L)
                        .name("Main UI")
                        .group(model.findGroup("UI"))
                        .protocol(Server.Protocol.HTTP)
                        .instances(Arrays.asList(
                                ApplicationInstance.builder().name("DEV CFIT").environment(model.findEnvironment("DEV")).applicationUrl("https://exp-cfit-dev-fwui.test-int.apps.czchooct002.dhl.com").build(),
                                ApplicationInstance.builder().name("DEV NZD").environment(model.findEnvironment("DEV")).applicationUrl("https://exp-nzd2-dev-fwui.test-int.apps.czchooct002.dhl.com").build(),
                                ApplicationInstance.builder().name("UAT CFIT").environment(model.findEnvironment("UAT")).applicationUrl("https://exp-cfit-uat-fwui.apps.czchoocp007.dhl.com").build(),
                                ApplicationInstance.builder().name("UAT NZD").environment(model.findEnvironment("UAT")).applicationUrl("https://exp-nzd2-uat-fwui.apps.czchoocp007.dhl.com").build(),
                                ApplicationInstance.builder().name("UAT Old").environment(model.findEnvironment("UAT")).applicationUrl("https://nzd2-crs-uat-fwui.dhl.com/dcc/pages/public/login.xhtml").build(),
                                ApplicationInstance.builder().name("QA CFIT").environment(model.findEnvironment("QA")).applicationUrl("https://exp-cfit-qa-fwui.prod-int.apps.czchoocp007.dhl.com/pages/public/login.xhtml").build(),
                                ApplicationInstance.builder().name("Local").environment(model.findEnvironment("LOCAL")).applicationUrl("https://localhost:2020/pages/public/login.xhtml").build()
                        ))
                        .propertiesGroups(Arrays.asList(
                                PropertyGroup.builder().id(1L).name("Database").properties(Arrays.asList(
                                        Property.builder().name("DEV DB").value("mysql dev").build(),
                                        Property.builder().name("UAT DB").value("mysql uat").build()
                                )).build(),
                                PropertyGroup.builder().id(2L).name("URLs").properties(Arrays.asList(
                                        Property.builder().name("Jenkins Root").value("https://jenkins-core-prg.dhl.com/cfit/job/root-folder/").build(),
                                        Property.builder().name("JIRA").value("https://jira.dhl.com/issues/?filter=55006").build()
                                )).build()
                        ))
                        .build());

        model.getApplications().add(
                Application.builder()
                        .id(2L)
                        .name("Dashboard UI")
                        .group(model.findGroup("UI"))
                        .protocol(Server.Protocol.HTTP)
                        .instances(Arrays.asList(
                                ApplicationInstance.builder().name("DEV").environment(model.findEnvironment("DEV")).applicationUrl("https://exp-cfit-dev-fwds.test-int.apps.czchooct002.dhl.com").build(),
                                ApplicationInstance.builder().name("Local").environment(model.findEnvironment("LOCAL")).applicationUrl("https://localhost:2020/pages/public/login.xhtml").build(),
                                ApplicationInstance.builder().name("QA").environment(model.findEnvironment("QA")).applicationUrl("https://exp-cfit-qa-fwds.prod-int.apps.czchoocp007.dhl.com/pages/public/login.xhtml").build()
                        ))
                        .build());


    }
}
