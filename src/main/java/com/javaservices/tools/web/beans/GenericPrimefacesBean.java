package com.javaservices.tools.web.beans;

import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.context.FacesContext;
import java.io.IOException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.event.TabChangeEvent;
import org.springframework.beans.factory.annotation.Value;

@Data
@Slf4j
public class GenericPrimefacesBean {

    @Value("#{request.getParameter('tabId')}")
    @ManagedProperty("tabId")
    protected Long tabId;

    protected void redirect(String url) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }

    public void onTabChange(TabChangeEvent<?> tabChangeEvent) throws IOException {
        log.info("Tab change");

        this.tabId = (long) tabChangeEvent.getTab().getParent().getChildren().indexOf(tabChangeEvent.getTab());

        String url = this.prepareUrl();

        this.redirect(url);
    }

    protected String prepareUrl() {
        throw new IllegalStateException("prepareUrl() method not implemented");
    }

}
