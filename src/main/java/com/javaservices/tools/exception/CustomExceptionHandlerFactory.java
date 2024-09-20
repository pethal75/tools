package com.javaservices.tools.exception;

import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerFactory;

public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory exceptionHandlerFactory;

    public CustomExceptionHandlerFactory(ExceptionHandlerFactory exceptionHandlerFactory) {
        this.exceptionHandlerFactory = exceptionHandlerFactory;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return new CustomExceptionHandler(exceptionHandlerFactory.getExceptionHandler());
    }
}