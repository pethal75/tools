package com.javaservices.tools.web.beans;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.model.applications.Property;
import com.javaservices.tools.model.applications.PropertyGroup;
import com.javaservices.tools.service.ApplicationsService;
import static com.javaservices.tools.web.beans.ApplicationDetailBean.tabPropertiesId;
import com.javaservices.tools.web.beans.primefaces.PrimefacesFormBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toCollection;
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

    public static final String pageUrl = "propertyDetail.xhtml";

    protected ApplicationsService applicationsService;

    @Value("#{request.getParameter('applicationId')}")
    @ManagedProperty("applicationId")
    protected Long applicationId;

    @Value("#{request.getParameter('name')}")
    @ManagedProperty("name")
    protected String name;

    protected String groupName, origGroupName;

    protected Application application;

    @Inject
    public PropertyDetailBean(ApplicationsService applicationsService) {
        this.applicationsService = applicationsService;
    }

    @PostConstruct
    public void init() {
        log.debug("initialize property : {}", name);

        application = applicationsService.findApplicationById(applicationId);

        Property property = application.findPropertyByName(name);

        if (property == null) {
            property = new Property();
        }

        this.groupName = property.getGroupName();
        this.origGroupName = property.getGroupName();

        this.initialize(property);
    }

    public List<String> completeGroup(String query) {
        String queryLowerCase = query.toLowerCase();

        if (application.getPropertiesGroups() == null)
            return List.of(query);

        ArrayList<String> currentGroups = application.getPropertiesGroups().stream().map(PropertyGroup::getName).collect(toCollection(ArrayList::new));
        currentGroups.add(query);

        return currentGroups.stream()
                .filter(groupName -> groupName.toLowerCase().startsWith(queryLowerCase))
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public String getUrl() {
        return pageUrl + "?applicationId="+ this.applicationId + "&name=" + this.name;
    }

    @Override
    public String getBackUrl() {
        return ApplicationDetailBean.pageUrl + MessageFormat.format("?id={0}&tabId={1}", application.getId(), tabPropertiesId);
    }

    public void save() throws IOException {
        log.debug("Saving property details {}", this.entity.getName());

        this.applicationsService.updateProperty(this.application, this.groupName, this.entity);

        this.redirect(getBackUrl());
    }

    public Map<String, Property.PropertyType> getTypes() {
        return Arrays.stream(Property.PropertyType.values())
                .collect(Collectors.toMap(Enum::toString, propertyType -> propertyType));
    }

    @Override
    public boolean isChanged() {
        return super.isChanged() || !this.groupName.equals(this.entity.getGroupName());
    }

    @Override
    public void cancel() throws IOException {
        this.entity = (Property) this.origEntity.clone();
        this.groupName = this.origGroupName;

        this.redirect(getUrl());
    }
}
