package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.ToolsModel;
import com.javaservices.tools.service.ToolsModelService;
import com.javaservices.tools.web.beans.primefaces.PrimefacesBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.model.file.UploadedFile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static com.javaservices.tools.model.ToolsCustomProperties.STORAGE_TYPE_FILE;

@Component
@ViewScoped
@Getter
@Setter
@Slf4j
public class ProjectDetailBean extends PrimefacesBean {

    public static final String pageUrl = "projectDetail.xhtml";

    protected ToolsModelService toolsModelService;

    protected ToolsModel projectModel;

    private UploadedFile file;

    @Inject
    public ProjectDetailBean(ToolsModelService toolsModelService) {
        this.toolsModelService = toolsModelService;
    }

    @PostConstruct
    public void init() {
        log.debug("initialize project : {}", toolsModelService.getModel().getName());

        this.projectModel = toolsModelService.getModel();
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List<String> getAvailableProjects() {
        List<String> projects = new ArrayList<>();

        if (this.toolsModelService.getCustomProperties().getStorageType().equals(STORAGE_TYPE_FILE)) {
            Path path = Paths.get(this.toolsModelService.getCustomProperties().getStorageDirectory());

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                for (Path file : stream) {
                    String name = file.getName(file.getNameCount()-1).toString();

                    if (name.endsWith(".json")) {
                        projects.add(name.replace(".json", ""));
                    }
                }
            } catch (IOException | DirectoryIteratorException ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        }

        return projects;
    }

    /*public void handleFileUpload(FileUploadEvent event) {
        FacesMessage message = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }*/

    public void importModel(String name) {
    }

    public void exportModel() {
    }

}
