package com.javaservices.tools.web.beans.converters;

import com.javaservices.tools.model.servers.Server;
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
@FacesConverter(value = "serverConverter")
public class ServerConverter implements Converter<Server> {

    @Autowired
    private ToolsModelService toolsModelService;

    @Override
    public Server getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return toolsModelService.getModel().findServerById(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Server server) {
        if (server == null) {
            return "";
        }
        return String.valueOf(server.getId());
    }
}