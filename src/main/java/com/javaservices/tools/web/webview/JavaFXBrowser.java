package com.javaservices.tools.web.webview;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JavaFXBrowser extends Application {
	private Scene scene;

	@Override
	public void start(Stage stage) {

		String title = getParameters().getUnnamed().get(0);
		String url = getParameters().getUnnamed().get(1);

		// create the scene
		stage.setTitle(title);
		scene = new Scene(new Browser(url), 1600, 800, Color.web("#666970"));
		stage.setScene(scene);
		//scene.getStylesheets().add("webviewsample/BrowserToolbar.css");
		stage.show();
	}

	public static void openBrowser(String titleParam, String urlParam) {
		launch(titleParam, urlParam);
	}

	@Override
	public void stop() {
		System.exit(0);
	}
}

