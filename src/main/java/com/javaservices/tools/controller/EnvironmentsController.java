package com.javaservices.tools.controller;

import com.javaservices.network.clients.HttpClientUtil;
import com.javaservices.tools.dhl.DhlModel;
import com.javaservices.tools.model.ToolsModel;
import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.ApplicationInstance;
import com.javaservices.tools.model.environments.Environment;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class EnvironmentsController {

    // TODO read from session storage
    protected ToolsModel toolsModel = new DhlModel();

    public List<Environment> getEnvironments() {
        return toolsModel.getEnvironments();
    }
}
