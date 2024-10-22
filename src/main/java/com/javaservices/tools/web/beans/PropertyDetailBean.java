package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.Property;
import com.javaservices.tools.model.applications.PropertyGroup;
import com.javaservices.tools.model.servers.Server;
import com.javaservices.tools.service.ApplicationsService;
import static com.javaservices.tools.web.beans.ApplicationDetailBean.tabPropertiesId;
import com.javaservices.tools.web.beans.primefaces.PrimefacesFormBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@EqualsAndHashCode
@Component
@ViewScoped
@Data
@Slf4j
public class PropertyDetailBean extends PrimefacesFormBean<Property> {

    public static final String pageUrl = "applicationPropertyDetail.xhtml";

    protected ApplicationsService applicationsService;

    @Value("#{request.getParameter('applicationId')}")
    @ManagedProperty("applicationId")
    protected Long applicationId;

    @Value("#{request.getParameter('groupId')}")
    @ManagedProperty("groupId")
    protected Long groupId;

    @Value("#{request.getParameter('name')}")
    @ManagedProperty("name")
    protected String name;

    protected Application application;

    protected PropertyGroup propertyGroup;

    @Inject
    public PropertyDetailBean(ApplicationsService applicationsService) {
        this.applicationsService = applicationsService;
    }

    @PostConstruct
    public void init() {
        log.debug("initialize property : {}", name);

        application = applicationsService.findApplicationById(applicationId);

        propertyGroup = application.findPropertyGroupById(groupId);

        Property property = propertyGroup.findPropertyByName(name);

        if (property == null) {
            property = new Property();
        }

        this.initialize(property);
    }

    public Map<String, Server.Protocol> getProtocols() {
        return Arrays.stream(Server.Protocol.values())
                .collect(Collectors.toMap(protocol -> protocol.toString(), protocol -> protocol));
    }

    public Map<String, Server.ServerType> getTypes() {
        return Arrays.stream(Server.ServerType.values())
                .collect(Collectors.toMap(serverType -> serverType.toString(), serverType -> serverType));
    }

    @Override
    public String getUrl() {
        return pageUrl;
    }

    @Override
    public String getBackUrl() {
        return ApplicationDetailBean.pageUrl + MessageFormat.format("?applicationId={0}&tabId={1}", application.getId(), tabPropertiesId);
    }

    public void save() throws IOException {
        log.debug("Saving property details {}", this.entity.getName());

        //

        this.redirect(getBackUrl());
    }
}
