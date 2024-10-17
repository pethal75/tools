package com.javaservices.tools;

import com.javaservices.tools.web.webview.JavaFXBrowser;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main spring boot class, that runs Spring application and window with JavaFX browser
 */
@SpringBootApplication
@ServletComponentScan
@ComponentScan(basePackages = {"com.javaservices"})
@EnableAsync
@EnableJms
public class ToolsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ToolsApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ToolsApplication.class);
    }

    @Value("${tools.javafx-browser.starting-url}")
    protected String mainUrl;

    @Value("${tools.javafx-browser.enabled}")
    protected boolean startJavaFXBrowser;

    @PostConstruct
    public void startup() {

        if (startJavaFXBrowser) {
            Runnable startJavaFX = () -> JavaFXBrowser.openBrowser("ServersApplication", this.mainUrl);

            new Thread(startJavaFX).start();
        }
    }
}
