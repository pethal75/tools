package com.javaservices.tools.web.beans.converters;

import com.javaservices.tools.model.applications.Application;
import com.javaservices.tools.service.ApplicationsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
@FacesConverter(value = "applicationConverterBean", managed = true)
public class ApplicationConverterBean implements Converter<Application> {

    @Inject
    private ApplicationsService applicationsService;

    @Override
    public Application getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.trim().isEmpty()) {
            return applicationsService.findApplicationByName(value);
        }
        else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Application value) {
        if (value != null) {
            return String.valueOf(value.getName());
        }
        else {
            return null;
        }
    }
}
