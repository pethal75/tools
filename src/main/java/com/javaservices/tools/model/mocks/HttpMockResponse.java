package com.javaservices.tools.model.mocks;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;

@Builder
@Data
public class HttpMockResponse {

    protected String name;

    // Request matching members
    protected String urlPattern;
    protected String bodyPattern;
    protected HttpMethod method;

    // Response definition
    protected HttpStatusCode responseCode;
    protected String responseBody;
    protected String responseContentType;
}
