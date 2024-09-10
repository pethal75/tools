package com.javaservices.tools.dhl;

import com.javaservices.tools.model.mocks.HttpMockResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class Umex {
    public static void setup(DhlModel model) {
        model.getMockResponses().add(
                HttpMockResponse.builder()
                        .name("umex-login")
                        .method(HttpMethod.POST)
                        .urlPattern("/umex")
                        .bodyPattern("authenticateUserDetails")
                        .responseCode(HttpStatus.OK)
                        .responseBody("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:authenticateUserDetailsResponse xmlns:ns2=\"http://ws.um.dhl.com/\"><result><user><id>1077058598</id><login>pehalada</login><firstName>Peter</firstName><lastName>Halada</lastName><email>peter.halada@dhl.com</email><language>ENG</language><numberOfRecord>20</numberOfRecord><roles><roles><id>286398968</id><name>Super User</name><label>ofr_super_user</label><userPermissions/><userDataLimitations/></roles></roles><changePasswordAtNextLogin>false</changePasswordAtNextLogin></user><respStatus><code>0</code><description>Success</description></respStatus></result></ns2:authenticateUserDetailsResponse></soap:Body></soap:Envelope>")
                        .build());

        model.getMockResponses().add(
                HttpMockResponse.builder()
                        .name("umex-get-user")
                        .method(HttpMethod.GET)
                        .urlPattern("/umex/ws/user")
                        .responseCode(HttpStatus.OK)
                        .responseBody("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:authenticateUserDetailsResponse xmlns:ns2=\"http://ws.um.dhl.com/\"><result><user><id>1077058598</id><login>pehalada</login><firstName>Peter</firstName><lastName>Halada</lastName><email>peter.halada@dhl.com</email><language>ENG</language><numberOfRecord>20</numberOfRecord><roles><roles><id>286398968</id><name>Super User</name><label>ofr_super_user</label><userPermissions/><userDataLimitations/></roles></roles><changePasswordAtNextLogin>false</changePasswordAtNextLogin></user><respStatus><code>0</code><description>Success</description></respStatus></result></ns2:authenticateUserDetailsResponse></soap:Body></soap:Envelope>")
                        .build());


    }
}
