package com.javaservices.tools.exception;

import jakarta.faces.context.FacesContext;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Managed bean handling showing the details of the exception when error occurs.
 * @author Radek Jezdik
 */
@Component("errorBean")
@Scope("request")
public class ErrorManagedBean {

    private Throwable exception;

    @PostConstruct
    public void init() {
        exception = (Throwable) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("exception");
    }

    public String getExceptionMessage() {
        if (exception == null) {
            return "n/a";
        }

        if (exception.getCause() != null) {
            return exception.getCause().getMessage();
        }

        return exception.getMessage();
    }

}
