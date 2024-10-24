package com.javaservices.tools.web.beans.converters;

import com.javaservices.tools.model.environments.Environment;
import com.javaservices.tools.service.ToolsModelService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ApplicationScoped
@FacesConverter(value = "environmentConverterBean", managed = true)
public class EnvironmentConverterBean implements Converter<Environment> {

    @Inject
    private ToolsModelService toolsModelService;

    @Override
    public Environment getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && !value.trim().isEmpty()) {
            return toolsModelService.getModel().findEnvironmentByName(value);
        }
        else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Environment value) {
        if (value != null) {
            return String.valueOf(value.getName());
        }
        else {
            return null;
        }
    }
}
