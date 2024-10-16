package com.javaservices.tools.model.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ActiveMQ {

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
}