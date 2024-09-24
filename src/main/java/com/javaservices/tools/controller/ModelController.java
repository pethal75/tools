package com.javaservices.tools.controller;

import com.javaservices.tools.dhl.DhlModel;
import com.javaservices.tools.model.ToolsModel;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ModelController {
    ToolsModel model = new DhlModel();

    public void loadModel() {

    }

    public void saveModel() {

    }
}
