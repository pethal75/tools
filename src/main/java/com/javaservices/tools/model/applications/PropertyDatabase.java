package com.javaservices.tools.model.applications;

import static com.javaservices.tools.utils.StringUtils.getPrintableString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class PropertyDatabase extends Property {

    public static final int PRINTABLE_VALUE_MAX_LENGTH = 40;

    protected String connection;

    protected String username;

    protected String password;

    @Override
    public String getPrintableValue() {
        return getPrintableString(this.connection, PRINTABLE_VALUE_MAX_LENGTH) + " (" + getPrintableString(this.username, -1) + " / *** )";
    }

    @Override
    public PropertyDatabase clone() {
        return this.toBuilder().build();
    }
}
