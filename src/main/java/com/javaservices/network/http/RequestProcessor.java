package com.javaservices.network.http;

import com.javaservices.tools.model.mocks.HttpMockResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Optional;
import org.springframework.http.HttpMethod;

public class RequestProcessor {

    public final Collection<HttpMockResponse> requests;

    public RequestProcessor(Collection<HttpMockResponse> mockResponses) {
        this.requests = mockResponses;
    }

    public Optional<HttpMockResponse> findMatchedRequest(HttpServletRequest request, String body) {
        return this.requests.stream()
                .filter(httpRequestMock -> isMatchingMockRequest(httpRequestMock, request, body))
                .findFirst();
    }

    private boolean isMatchingMockRequest(HttpMockResponse httpMockResponse, HttpServletRequest request, String body) {
        if (httpMockResponse.getMethod() != null && !httpMockResponse.getMethod().equals(HttpMethod.valueOf(request.getMethod())))
            return false;

        String url = request.getRequestURL() + "?" + request.getQueryString();
        if (httpMockResponse.getUrlPattern() != null && !url.contains(httpMockResponse.getUrlPattern()))
            return false;

        if (httpMockResponse.getBodyPattern() != null && body != null && !body.contains(httpMockResponse.getBodyPattern()))
            return false;

        return true;
    }
}
