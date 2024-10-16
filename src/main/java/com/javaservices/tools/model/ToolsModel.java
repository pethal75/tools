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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public void loadConfiguration(String fileName) {
        XStream xstream = prepareXStream();
    /*
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        xstream.alias("product", Product.class);
        Product product = (Product) xstream.fromXML(json);
        System.out.println(product.getName());
     */

    }

    public void saveConfiguration(String fileName) {
        XStream xstream = prepareXStream();

        File saveFile = new File(fileName);
        FileWriter saveWriter;
        try {
            saveWriter = new FileWriter(saveFile);

            xstream.toXML(this, saveWriter);

            saveWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static XStream prepareXStream() {
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.alias("ToolsConfiguration", ToolsModel.class);
        xstream.alias("Application", Application.class);
        xstream.alias("ApplicationInstance", ApplicationInstance.class);
        xstream.alias("Server", Server.class);
        xstream.alias("MockRequest", HttpMockResponse.class);

        xstream.addImplicitArray(Application.class, "instances", ApplicationInstance.class);
        xstream.addImplicitArray(Application.class, "propertiesGroups", PropertyGroup.class);
        xstream.addImplicitArray(PropertyGroup.class, "properties", Property.class);

        xstream.omitField(ApplicationInstance.class, "application");
        xstream.omitField(Property.class, "group");

        return xstream;
    }
}
