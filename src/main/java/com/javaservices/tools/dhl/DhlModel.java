package com.javaservices.tools.dhl;

import com.javaservices.tools.model.ToolsModel;
import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.environments.Environment;
import com.javaservices.tools.model.servers.Server;
import org.springframework.stereotype.Component;

@Component
public class DhlModel extends ToolsModel {

    public DhlModel() {

        // TODO setup related to applications, add name
        this.servers.add(Server.builder().host("aaaprg.prg-dc.dhl.com").protocol(Server.Protocol.SSH).serverType(Server.ServerType.LINUX).build());
        this.servers.add(Server.builder().host("exp-cfit-dev-fwds.test-int.apps.czchooct002.dhl.com/").protocol(Server.Protocol.HTTPS).serverType(Server.ServerType.WEB).build());
        this.servers.add(Server.builder().host("exp-cfit-dev-fwui.test-int.apps.czchooct002.dhl.com/").protocol(Server.Protocol.HTTPS).serverType(Server.ServerType.WEB).build());

        // Login request mock
        Umex.setup(this);

        WebMethods.setup(this);

        this.environments.add(Environment.builder().name("LOCAL").build());
        this.environments.add(Environment.builder().name("DEV").build());
        this.environments.add(Environment.builder().name("UAT").build());
        this.environments.add(Environment.builder().name("QA").build());
        this.environments.add(Environment.builder().name("PROD").build());

        OfflineRating.setup(this);

        UIApplications.setup(this);

        AmfMonitoring.setup(this);

        Translators.setup(this);

        X400Connector.setup(this);

        this.applications.forEach(Application::initialize);

        this.saveConfiguration("config.json");
    }

}
