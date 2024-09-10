package com.javaservices.tools.model.servers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Server {

    protected String name;
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
