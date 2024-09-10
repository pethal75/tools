package com.javaservices.tools.web.webview;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

class Browser extends Region {

    final WebView webView = new WebView();
    final WebEngine webEngine = webView.getEngine();

    public Browser(String url) {
        // apply the styles
        getStyleClass().add("browser");
        // load the web page
        webEngine.load(url);
        // add the web view to the scene
        getChildren().add(webView);

    }

    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    @Override
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(webView, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
    }

    @Override
    protected double computePrefWidth(double height) {
        return 1600;
    }

    @Override
    protected double computePrefHeight(double width) {
        return 500;
    }
}
