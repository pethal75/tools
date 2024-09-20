package com.javaservices.tools.controller;

import com.javaservices.network.http.RequestProcessor;
import com.javaservices.tools.model.mocks.HttpMockResponse;
import com.javaservices.tools.model.ToolsModel;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ProxyController {

    protected ToolsModel configuration;

    protected final RequestProcessor requestProcessor;

    public ProxyController(ToolsModel configuration) {
        this.configuration = configuration;

        this.requestProcessor = new RequestProcessor(configuration.getMockResponses());
    }

    /**
     * GET requests mocking
     *
     * @param request http servlet request
     * @return mocked response
     */
    @GetMapping(value = "/proxyController/**")
    public ResponseEntity<String> proxyGetRequest(HttpServletRequest request) {

        log.info(">>> Proxy controller GET method <<<");

        return this.prepareResponseEntity("", request);
    }

    /**
     * POST requests mocking
     *
     * @param body request body
     * @param request http servlet request
     * @return mocked response
     */
    @PostMapping(value = "/proxyController/**")
    public ResponseEntity<String> proxyPostRequest(@RequestBody String body, HttpServletRequest request) {

        log.info(">>> Proxy controller POST method <<<");

        return prepareResponseEntity(body, request);
    }

    /**
     * PUT requests mocking
     *
     * @param body request body
     * @param request http servlet request
     * @return mocked response
     */
    @PutMapping(value = "/proxyController/**")
    public ResponseEntity<String> proxyPutRequest(@RequestBody String body, HttpServletRequest request) {

        log.info(">>> Proxy controller PUT method <<<");

        return prepareResponseEntity(body, request);
    }

    /**
     * PATCH requests mocking
     *
     * @param body request body
     * @param request http servlet request
     * @return mocked response
     */
    @PatchMapping(value = "/proxyController/**")
    public ResponseEntity<String> proxyPatchRequest(@RequestBody String body, HttpServletRequest request) {

        log.info(">>> Proxy controller PATCH method <<<");

        return this.prepareResponseEntity(body, request);
    }


    /**
     * Find mock request and prepare response
     *
     * @param body request body
     * @param request servlet request
     * @return ResponseEntity
     */
    private ResponseEntity<String> prepareResponseEntity(String body, HttpServletRequest request) {

        log.debug("Processing request {}", request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString(): ""));

        Optional<HttpMockResponse> mockResponseOptional = requestProcessor.findMatchedRequest(request, body);

        if (mockResponseOptional.isEmpty()) {
            log.warn("Mock response not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mock request not matched");
        }

        log.debug("Response prepared");

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin","*");

        return ResponseEntity
                .status(mockResponseOptional.get().getResponseCode())
                .headers(responseHeaders)
                .body((mockResponseOptional.get().getResponseBody()));
    }
}
