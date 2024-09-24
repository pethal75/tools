package com.javaservices.tools.web.beans.primefaces;

import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.context.FacesContext;
import java.io.IOException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.event.TabChangeEvent;
import org.springframework.beans.factory.annotation.Value;

@Data
@Slf4j
public class PrimefacesBean {

    public void redirect(String url) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
    }
}
