package com.javaservices.tools.web.beans.converters;

import com.javaservices.tools.model.environments.Group;
import com.javaservices.tools.service.ToolsModelService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Named
@ApplicationScoped
@FacesConverter(value = "groupConverter")
public class GroupConverter implements Converter<Group> {

    @Autowired
    private ToolsModelService toolsModelService;

    @Override
    public Group getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return toolsModelService.getModel().findGroupById(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Group group) {
        if (group == null) {
            return "";
        }
        return String.valueOf(group.getId());
    }
}