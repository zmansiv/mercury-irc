package com.mercuryirc.client.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class TitleBar extends HBox {

	private final Stage stage;

	public TitleBar(Stage stage) throws IOException {
		this.stage = stage;
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
		minimizeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				TitleBar.this.stage.setIconified(true);
			}
		});
		final Button maximizeButton = new Button();
		maximizeButton.setId("maximize-button");
		maximizeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				toggleMaximization(TitleBar.this.stage, maximizeButton);
			}
		});
		Button closeButton = new Button();
		closeButton.setId("close-button");
		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				TitleBar.this.stage.close();
			}
		});
		getChildren().addAll(settingsButton, spacer1, titleLabel, spacer2, minimizeButton, maximizeButton, closeButton);
	}

	private void toggleMaximization(Stage stage, Button button) {
		Screen screen = Screen.getPrimary();
		Rectangle2D maximized = screen.getVisualBounds();
		if (stage.getWidth() == maximized.getWidth() && stage.getHeight() == maximized.getHeight()) {
			stage.setX(50);
			stage.setY(50);
			stage.setWidth(1100);
			stage.setHeight(600);
			button.setId("maximize-button");
		} else {
			stage.setX(maximized.getMinX());
			stage.setY(maximized.getMinY());
			stage.setWidth(maximized.getWidth());
			stage.setHeight(maximized.getHeight());
			button.setId("restore-button");
		}
	}

}