package com.javaservices.tools.model;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.model.applications.Property;
import com.javaservices.tools.model.applications.PropertyDatabase;
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
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

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

        // Replace referenced entities of Application.group, ApplicationInstance.environment, ApplicationInstance.server with referenced objects
        this.applications.forEach(application -> {
            application.initialize();

            if (application.getGroup() != null)
                application.setGroup(findGroupById(application.getGroup().getId()));

            application.getInstances().forEach(applicationInstance -> {
                if (applicationInstance.getEnvironment() != null)
                    applicationInstance.setEnvironment(findEnvironmentById(applicationInstance.getEnvironment().getId()));
                if (applicationInstance.getServer() != null)
                    applicationInstance.setServer(findServerById(applicationInstance.getServer().getId()));
            });
        });

        if (environments != null)
            this.environments.sort(Comparator.comparing(Environment::getName));

        if (groups != null)
            this.groups.sort(Comparator.comparing(Group::getName));

        if (servers != null)
            this.servers.sort(Comparator.comparing(Server::getName));
    }

    public Application findApplicationByName(String name) {
        if (applications == null || name == null)
            return null;

        return this.applications.stream()
                .filter(application -> application.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    public Environment findEnvironmentById(Long id) {
        if (environments == null || id == null)
            return null;

        return this.environments.stream()
                .filter(group -> group.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public Environment findEnvironmentByName(String name) {

        if (environments == null || name == null)
            return null;

        return this.environments.stream()
                .filter(environment -> environment.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    public Group findGroupById(Long id) {
        if (groups == null || id == null)
            return null;

        return this.groups.stream()
                .filter(group -> group.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public Group findGroupByName(String name) {
        if (groups == null || name == null)
            return null;

        return this.groups.stream()
                .filter(group -> group.getName().equals(name))
                .findAny()
                .orElse(null);
    }

    public Server findServerById(Long id) {
        if (servers == null || id == null) {
            return null;
        }
        return servers.stream()
                .filter(server -> id.equals(server.getId()))
                .findFirst()
                .orElse(null);
    }

    public Server findServerByName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        return servers.stream()
                .filter(server -> name.equals(server.getName()))
                .findFirst()
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
        xstream.alias("PropertyDatabase", PropertyDatabase.class);
        xstream.alias("Environment", Environment.class);
        xstream.alias("Group", Group.class);

        xstream.addImplicitArray(Application.class, "instances", ApplicationInstance.class);
        xstream.addImplicitArray(Application.class, "propertiesGroups", PropertyGroup.class);
        xstream.addImplicitArray(PropertyGroup.class, "properties", Property.class);

        xstream.omitField(ApplicationInstance.class, "application");
        xstream.omitField(PropertyGroup.class, "application");
        xstream.omitField(Property.class, "group");

        xstream.allowTypesByWildcard(new String[] {"com.javaservices.**"});

        return xstream;
    }

}
