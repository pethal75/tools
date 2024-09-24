package com.javaservices.tools.model.servers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Server {

    protected Long id;
    protected String name;
    protected String description;
    protected String host;
    protected Integer port;
    protected Protocol protocol;
    protected String healthcheckEndpoint;
    protected ServerType serverType;

    public enum Protocol {
        HTTP, HTTPS, TELNET, SSH
    }

    public enum ServerType {
        WEB, LINUX, WINDOWS
    }
}
