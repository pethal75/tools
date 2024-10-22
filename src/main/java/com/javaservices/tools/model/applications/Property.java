package com.javaservices.tools.model.applications;

import com.javaservices.tools.web.beans.primefaces.EditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Property implements EditableEntity {

    @ToString.Include
    protected String name;

    @ToString.Include
    protected String value;

    @Override
    public EditableEntity clone() {
        return this.toBuilder().build();
    }

    public String getGroupName() {
        if (this.group == null)
            return "";

        return this.group.getName();
    }

    public enum PropertyType {
        NUMBER, TEXT, URL, DATABASE
    }

    @ToString.Include
    protected PropertyType type;

    protected PropertyGroup group;

    public Long getGroupId() {
        return this.group.getId();
    }

    public Long getApplicationId() {
        return this.group.getApplication().getId();
    }
}
