package com.javaservices.tools.model.applications;

import com.javaservices.tools.model.environments.Environment;
import java.util.ArrayList;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString(onlyExplicitlyIncluded = true)
public class ApplicationInstance {

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
}
