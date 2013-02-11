package com.mercuryirc.client.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.io.File;
import java.io.IOException;

public class TitleBar extends HBox {

	public TitleBar() throws IOException {
		super();
		getStylesheets().add(new File("./res/css/TitleBar.css").toURI().toURL().toExternalForm());
		setId("title-bar");
		setMinHeight(28);
		setMaxHeight(28);
		Button settingsButton = new Button();
		settingsButton.setId("settings-button");
		Region spacer1 = new Region();
		HBox.setHgrow(spacer1, Priority.ALWAYS);
		Label titleLabel = new Label("Mercury");
		titleLabel.setId("title");
		Region spacer2 = new Region();
		HBox.setHgrow(spacer2, Priority.ALWAYS);
		Button minimizeButton = new Button();
		minimizeButton.setId("minimize-button");
		Button maximizeButton = new Button();
		maximizeButton.setId("maximize-button");
		Button closeButton = new Button();
		closeButton.setId("close-button");
		getChildren().addAll(settingsButton, spacer1, titleLabel, spacer2, minimizeButton, maximizeButton, closeButton);
	}

}