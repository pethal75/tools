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
