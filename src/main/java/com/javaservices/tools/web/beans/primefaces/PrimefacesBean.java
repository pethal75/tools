package com.javaservices.tools.web.beans.primefaces;

import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import java.io.IOException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class PrimefacesBean {

    public void redirect(String url) throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        if (url.startsWith("/"))
            externalContext.redirect(externalContext.getApplicationContextPath() + url);
        else
            externalContext.redirect(url);
    }

    public boolean isChanged() {
        return true;
    }
}
