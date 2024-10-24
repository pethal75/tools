package com.javaservices.tools.model.applications;

import static com.javaservices.tools.utils.StringUtils.getPrintableString;
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

    public static final int PRINTABLE_VALUE_MAX_LENGTH = 50;

    @ToString.Include
    protected String name;

    @ToString.Include
    protected String value;

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

    public String getPrintableValue() {
        return getPrintableString(this.value, PRINTABLE_VALUE_MAX_LENGTH);
    }

    @Override
    public Property clone() {
        return this.toBuilder().build();
    }
}
