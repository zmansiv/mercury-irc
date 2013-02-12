package com.mercuryirc.client;

import com.mercuryirc.client.ui.ApplicationPane;
import com.mercuryirc.client.ui.TitleBar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;

public class Mercury extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Mercury");
		stage.initStyle(StageStyle.TRANSPARENT);
		VBox content = new VBox();
		content.getChildren().add(new TitleBar());
		content.getChildren().add(new ApplicationPane());
		Scene scene = new Scene(content, 1100, 600);
		scene.setFill(null);
		scene.getStylesheets().add(new File("./res/css/Mercury.css").toURI().toURL().toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

}