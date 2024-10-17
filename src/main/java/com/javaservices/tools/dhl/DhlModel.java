package com.javaservices.tools.dhl;

import com.javaservices.tools.model.ToolsModel;
import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.environments.Environment;
import com.javaservices.tools.model.servers.Server;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class DhlModel extends ToolsModel {

    public DhlModel() {
        super();

        this.name = "DHL applications project";
        this.description = "DHL applications project";

        // TODO setup relations to applications/instances, add name
        this.servers.add(Server.builder().id(1L).name("AAA Gateway").host("aaaprg.prg-dc.dhl.com").protocol(Server.Protocol.SSH).serverType(Server.ServerType.LINUX).build());
        this.servers.add(Server.builder().id(2L).name("czcholsint3246").host("czcholsint3246").protocol(Server.Protocol.SSH).serverType(Server.ServerType.LINUX).build());
        this.servers.add(Server.builder().id(3L).name("czcholsint3242").host("czcholsint3242").protocol(Server.Protocol.SSH).serverType(Server.ServerType.LINUX).build());
        this.servers.add(Server.builder().id(4L).name("czcholsint3245").host("czcholsint3245").protocol(Server.Protocol.SSH).serverType(Server.ServerType.LINUX).build());
        this.servers.add(Server.builder().id(5L).name("czcholsint4357").host("czcholsint4357").protocol(Server.Protocol.SSH).serverType(Server.ServerType.LINUX).build());
        this.servers.add(Server.builder().id(6L).name("czcholsint2428").host("czcholsint2428").protocol(Server.Protocol.SSH).serverType(Server.ServerType.LINUX).build());

        // Login request mock
        Umex.setup(this);

        WebMethods.setup(this);

        this.environments.add(Environment.builder().id(1L).name("LOCAL").build());
        this.environments.add(Environment.builder().id(2L).name("DEV").description("Development purpose environment").build());
        this.environments.add(Environment.builder().id(3L).name("UAT").description("Uaser acceptance testing environment").build());
        this.environments.add(Environment.builder().id(4L).name("QA").description("Quality assurance environment").build());
        this.environments.add(Environment.builder().id(5L).name("PROD").description("Production environment").build());

        OfflineRating.setup(this);

        UIApplications.setup(this);

        AmfMonitoring.setup(this);

        Translators.setup(this);

        X400Connector.setup(this);

        this.initialize();

        //this.saveModel("dhl.json");
    }

}
