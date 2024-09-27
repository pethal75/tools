package com.javaservices.tools.service;

import com.javaservices.tools.dhl.DhlModel;
import com.javaservices.tools.model.ToolsModel;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

@Component
@Data
@ApplicationScope
public class ToolsModelService {

    private ToolsModel model = new DhlModel();

    private String actualPath;

    public void loadModel(String path) {

    }

    public void saveModel() {

    }
}
