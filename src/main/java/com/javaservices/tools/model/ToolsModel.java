package com.javaservices.tools.model;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.model.applications.Property;
import com.javaservices.tools.model.applications.PropertyGroup;
import com.javaservices.tools.model.environments.Environment;
import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.model.messaging.ActiveMQ;
import com.javaservices.tools.model.mocks.HttpMockResponse;
import com.javaservices.tools.model.servers.Server;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import javax.tools.Tool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ToolsModel {

    protected String name;

    protected String description;

    /**
     * Array of servers
     */
    protected List<Server> servers = new ArrayList<>();

    /**
     * Array of applications
     */
    protected List<Application> applications = new ArrayList<>();

    /**
     * Array of environments
     */
    protected List<Environment> environments = new ArrayList<>();

    /**
     * Array of groups
     */
    protected List<Group> groups = new ArrayList<>();

    protected ActiveMQ embeddedActiveMQ = ActiveMQ.setupEmbeddedActiveMQ();

    /**
     * Mocks of http requests/responses
     */
    protected List<HttpMockResponse> mockResponses = new ArrayList<>();

    public ToolsModel() {

    }

    protected void initialize() {

        this.applications.forEach(Application::initialize);

        AtomicLong index = new AtomicLong(1);

        this.getApplications().stream()
                .flatMap(applicationConfiguration -> applicationConfiguration.getInstances().stream())
                .forEach(applicationInstance -> applicationInstance.setId(index.getAndIncrement()));

    }

    public Environment findEnvironment(String name) {
        return this.environments.stream()
                .filter(environment -> environment.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    public Group findGroup(String name) {
        return this.groups.stream()
                .filter(group -> group.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    public static ToolsModel loadModel(String filePath) {
        XStream xstream = prepareXStream();

        File loadFile = new File(filePath);

        try (FileReader reader = new FileReader(loadFile)){

            ToolsModel model = (ToolsModel) xstream.fromXML(reader);

            model.initialize();

            return model;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveModel(String filePath) {
        XStream xstream = prepareXStream();

        File saveFile = new File(filePath);

        try (FileWriter writer = new FileWriter(saveFile);){

            xstream.toXML(this, writer);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static XStream prepareXStream() {
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.alias("Project", ToolsModel.class);
        xstream.alias("Application", Application.class);
        xstream.alias("ApplicationInstance", ApplicationInstance.class);
        xstream.alias("Server", Server.class);
        xstream.alias("MockRequest", HttpMockResponse.class);
        xstream.alias("PropertyGroup", PropertyGroup.class);
        xstream.alias("Property", Property.class);
        xstream.alias("Environment", Environment.class);
        xstream.alias("Group", Group.class);

        xstream.addImplicitArray(Application.class, "instances", ApplicationInstance.class);
        xstream.addImplicitArray(Application.class, "propertiesGroups", PropertyGroup.class);
        xstream.addImplicitArray(PropertyGroup.class, "properties", Property.class);

        xstream.omitField(ApplicationInstance.class, "application");
        xstream.omitField(Property.class, "group");

        xstream.allowTypesByWildcard(new String[] {"com.javaservices.**"});

        return xstream;
    }
}
