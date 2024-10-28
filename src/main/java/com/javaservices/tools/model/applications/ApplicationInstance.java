package com.javaservices.tools.model.applications;

import com.javaservices.tools.model.environments.Environment;
import com.javaservices.tools.model.servers.Server;
import com.javaservices.tools.utils.StringUtils;
import com.javaservices.tools.web.beans.primefaces.EditableEntity;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
@ToString(onlyExplicitlyIncluded = true)
public class ApplicationInstance implements EditableEntity {

    protected Long id;

    @ToString.Include
    protected String name;

    protected Application application;

    @ToString.Include
    protected String applicationUrl;

    protected ArrayList<Property> properties;

    public void initialize(Application application) {
        this.application = application;
    }

    public enum Status {
        UP, DOWN
    }

    protected Status status;

    public String getStatusImage() {
        return status == Status.UP ? "green-circle.png" : "red-circle.png";
    }

    protected Environment environment;

    protected Server server;

    public String getServerName() {
        return this.server != null ? StringUtils.getPrintableString(this.server.getName()) : "";
    }

    @Override
    public EditableEntity clone() {
        return this.toBuilder().build();
    }

}
