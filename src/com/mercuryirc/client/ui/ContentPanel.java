package com.mercuryirc.client.ui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;

public class ContentPanel extends VBox {

	public ContentPanel() throws IOException {
		getStylesheets().add(new File("./res/css/ApplicationPane.css").toURI().toURL().toExternalForm());
		setMinWidth(175);
		VBox contentBox = new VBox();
		contentBox.getStyleClass().add("content-box");
		VBox.setVgrow(contentBox, Priority.ALWAYS);
		HBox bottomBar = new HBox();
		bottomBar.getStyleClass().add("bottom-bar");
		bottomBar.setMinHeight(28);
		bottomBar.setMaxHeight(28);
		getChildren().addAll(contentBox, bottomBar);
	}

}
