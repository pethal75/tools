package com.javaservices.tools.model.messaging;

import com.javaservices.tools.web.beans.primefaces.EditableEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
public class ActiveMQ implements EditableEntity {

    protected Long id;
    protected boolean enabled = false;
    protected String brokerName;
    protected String host;
    protected Integer port;
    protected String user;
    protected String password;

    public static ActiveMQ setupEmbeddedActiveMQ() {
        return ActiveMQ.builder()
                .id(1L)
                .enabled(false)
                .brokerName("LocalActiveMQBroker")
                .host("localhost")
                .port(61616)
                .build();
    }

    @Override
    public ActiveMQ clone() {
        return this.toBuilder().build();
    }
}