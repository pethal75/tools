package com.javaservices.network.clients;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SNIMatcher;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Slf4j
public class HttpClientUtil {

    private static final int CONNECT_TIMEOUT_MILLIS = 3000;

    public WebClient.RequestBodySpec httpCall(String url, String method) {

        try {
            // Skip SSL hostname verifications
            SslContext sslContext = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE)
                    .build();

            HttpClient httpClient = HttpClient.create()
                .secure(sslContextSpec -> sslContextSpec
                        .sslContext(sslContext)
                        .handlerConfigurator(sslHandler -> {
                            SSLEngine engine = sslHandler.engine();
                            //engine.setNeedClientAuth(true);
                            SSLParameters params = new SSLParameters();
                            List<SNIMatcher> matchers = new LinkedList<>();

                            SNIMatcher matcher = new SNIMatcher(0) {
                                @Override
                                public boolean matches(SNIServerName serverName) {
                                    return true;
                                }
                            };

                            matchers.add(matcher);
                            params.setSNIMatchers(matchers);
                            engine.setSSLParameters(params);
                        }))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT_MILLIS)
                .responseTimeout(Duration.ofMillis(CONNECT_TIMEOUT_MILLIS))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)));

            WebClient client = WebClient.builder()
                    .clientConnector(new ReactorClientHttpConnector(httpClient))
                    .baseUrl(url)
                    .build();

            return client.method(HttpMethod.valueOf(method))
                    .accept(MediaType.APPLICATION_JSON);

        } catch (Exception e) {
            log.error("HttpCall failure", e);

            throw new RuntimeException(e);
        }
    }
}
