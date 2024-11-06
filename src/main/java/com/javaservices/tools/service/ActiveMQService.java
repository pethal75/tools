package com.javaservices.tools.service;

import com.javaservices.tools.model.messaging.ActiveMQ;
import jakarta.annotation.PostConstruct;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

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
 *
 * Web console
 *  https://stackoverflow.com/questions/44108318/spring-boot-start-activemq-web-console-on-startup
 *  https://github.com/hawtio/hawtio/tree/2.x/examples/springboot
 *  https://github.com/JoseLuisSR/springboot-activemq?tab=readme-ov-file
 *
 *  Hawtio console
 *   http://localhost:7000/tools/actuator/hawtio
 */
@Component
@DependsOn("toolsModelService")
@Slf4j
public class ActiveMQService {

    private final ToolsModelService toolsModelService;

    private final ApplicationContext applicationContext;

    private BrokerService broker;

    public ActiveMQService(ToolsModelService toolsModelService, ApplicationContext applicationContext) {
        this.toolsModelService = toolsModelService;
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void initialize() throws Exception {
        if (getEmbeddedActiveMQ() != null && getEmbeddedActiveMQ().isEnabled()) {
            registerBroker(getEmbeddedActiveMQ());
        }
    }

    public ActiveMQ getEmbeddedActiveMQ() {
        return toolsModelService.getModel().getEmbeddedActiveMQ();
    }

    public void updateEmbeddeedActiveMQ(ActiveMQ activeMQ) throws Exception {
        this.toolsModelService.getModel().setEmbeddedActiveMQ(activeMQ);

        this.toolsModelService.saveModel();

        destroyBroker();

        if (activeMQ.isEnabled()) {
            registerBroker(activeMQ);
        }
    }

    public void registerBroker(ActiveMQ activeMQ) throws Exception {
        String brokerUrl = "tcp://" + activeMQ.getHost() + ":" + activeMQ.getPort();

        log.debug("registerBroker on url: {}", brokerUrl);

        broker = new BrokerService();
        broker.setBrokerName(activeMQ.getBrokerName());
        TransportConnector connector = new TransportConnector();
        connector.setUri(new URI(brokerUrl));
        broker.addConnector(connector);
        broker.setUseJmx(true);
        //broker.getManagementContext().setCreateConnector(true);
        broker.autoStart();

        //BeanDefinitionRegistry factory = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
        //((SingletonBeanRegistry) factory).registerSingleton(BrokerService.class.getCanonicalName(), broker);

        ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
        beanFactory.registerSingleton(BrokerService.class.getCanonicalName(), broker);
    }

    public void destroyBroker() throws Exception {

        if (broker == null)
            return;

        log.debug("destroyBroker {}", broker.getBrokerName());

        broker.stop();

        BeanDefinitionRegistry factory = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
        ((DefaultListableBeanFactory) factory).destroySingleton(BrokerService.class.getCanonicalName());
    }

    /*
        @Bean
    public ApplicationRunner runner(JmsTemplate template) {
        return args -> template.convertAndSend("foo", "AMessage");
    }

    @JmsListener(destination = "foo")
    public void listen(String in) {
        System.out.println(in);
    }
     */
}
