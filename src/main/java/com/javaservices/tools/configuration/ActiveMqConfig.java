package com.javaservices.tools.configuration;

import java.net.URI;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;

/**
 * https://docs.spring.io/spring-boot/docs/2.0.x/reference/html/boot-features-messaging.html
 * https://codeaches.com/spring-boot/embedded-activemq-5-jms-broker
 * https://activemq.apache.org/components/classic/documentation/how-do-i-embed-a-broker-inside-a-connection
 *
 * Active MQ
 *  https://stackoverflow.com/questions/48504265/is-it-possible-to-connect-to-spring-boot-embedded-activemq-instance-from-another
 *
 * Artemis
 *  https://stackoverflow.com/questions/76670206/embedded-activemq-artemis-doesnt-show-web-console
 */
@Configuration
@EnableJms
public class ActiveMqConfig {

    @Value("${spring.activemq.broker-url}")
    String brokerUrl;
    @Value("${spring.activemq.user}")
    String user;
    @Value("${spring.activemq.password}")
    String password;

    @Bean
    public BrokerService broker() throws Exception {

        BrokerService broker = new BrokerService();
        TransportConnector connector = new TransportConnector();
        connector.setUri(new URI(brokerUrl));
        broker.addConnector(connector);
        return broker;
    }

    @Bean
    public ApplicationRunner runner(JmsTemplate template) {
        return args -> template.convertAndSend("foo", "AMessage");
    }

    @JmsListener(destination = "foo")
    public void listen(String in) {
        System.out.println(in);
    }

}
