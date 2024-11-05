package com.javaservices.tools.service;

import com.javaservices.tools.model.ToolsCustomProperties;
import com.javaservices.tools.model.ToolsModel;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.DefaultPropertiesPersister;

@Component("toolsModelService")
@Data
@Scope("singleton")
@Slf4j
public class ToolsModelService {

    private ToolsModel model;

    private ToolsCustomProperties customProperties = new ToolsCustomProperties();

    public ToolsModel getModel() {
        if (this.model == null) {
            this.initialize();
        }

        return this.model;
    }

    @PostConstruct
    public void initialize() {
        loadCustomProperties();

        if (customProperties.lastProject != null && !customProperties.lastProject.isEmpty()) {
            this.loadModel(customProperties.lastProject);
        } else {
            this.createModel("New tools project");
        }
    }

    public void createModel(String name) {
        this.model = new ToolsModel();
        this.model.setName(name);
    }

    public void loadModel(String name) {
        if (customProperties == null)
            loadCustomProperties();

        this.model = ToolsModel.loadModel(customProperties.storageDirectory + File.separator + name + ".json");
    }

    public void saveModel() {
        this.model.saveModel(customProperties.storageDirectory + File.separator + this.model.getName() + ".json");

        customProperties.lastProject = this.model.getName();

        // Save property file
        saveCustomProperties();
    }

    public void loadCustomProperties() {
        try {
            Properties props = new Properties();
            File f = new File("./tools.properties");
            FileReader in = new FileReader( f );
            // write into it
            DefaultPropertiesPersister p = new DefaultPropertiesPersister();
            p.load(props, in);

            customProperties.storageType = props.getProperty("tools.storage.type");
            customProperties.storageDirectory = props.getProperty("tools.storage.directory");
            customProperties.lastProject = props.getProperty("tools.last-project");

        } catch (Exception e ) {
            log.error("Exception during loading property file", e);
        }
    }

    public void saveCustomProperties() {
        try {
            // create and set properties into properties object
            Properties props = new Properties();
            props.setProperty("tools.storage.type", customProperties.storageType);
            props.setProperty("tools.storage.directory", customProperties.storageDirectory);
            props.setProperty("tools.last-project", customProperties.lastProject);
            // get or create the file
            File f = new File("./tools.properties");
            FileWriter writer = new FileWriter( f );
            // write into it
            DefaultPropertiesPersister p = new DefaultPropertiesPersister();
            p.store(props, writer, "Tools properties");
        } catch (Exception e ) {
            log.error("Exception during saving property file", e);
        }
    }
}
