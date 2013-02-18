package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import com.mercuryirc.client.ui.misc.FontAwesome;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TitlePane extends StackPane {

	private final Stage stage;

	public TitlePane(Stage stage) {
		this.stage = stage;
		getStylesheets().add(Mercury.class.getResource("./res/css/TitlePane.css").toExternalForm());
		setId("title-bar");
		setMinHeight(50);
		setMaxHeight(50);
		Label titleLabel = new Label("mercury");
		titleLabel.setId("title");
		HBox buttonBox = new HBox();
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		Button minimizeButton = FontAwesome.createIconButton(FontAwesome.MINUS);
		minimizeButton.setId("minimize-button");
		minimizeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				TitlePane.this.stage.setIconified(true);
			}
		});
		final Button maximizeButton = FontAwesome.createIconButton(FontAwesome.RESIZE_FULL);
		maximizeButton.setId("maximize-button");
		maximizeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				toggleMaximization(TitlePane.this.stage, maximizeButton);
			}
		});
		Button closeButton = FontAwesome.createIconButton(FontAwesome.REMOVE);
		closeButton.setId("close-button");
		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				TitlePane.this.stage.close();
			}
		});
		buttonBox.getChildren().addAll(spacer, minimizeButton, maximizeButton, closeButton);
		getChildren().addAll(titleLabel, buttonBox);
	}

	private void toggleMaximization(Stage stage, Button button) {
		Screen screen = Screen.getPrimary();
		Rectangle2D maximized = screen.getVisualBounds();
		if (stage.getWidth() == maximized.getWidth() && stage.getHeight() == maximized.getHeight()) {
			stage.setX(50);
			stage.setY(50);
			stage.setWidth(1100);
			stage.setHeight(600);
			button.setGraphic(FontAwesome.createIcon(FontAwesome.RESIZE_FULL));
		} else {
			stage.setX(maximized.getMinX());
			stage.setY(maximized.getMinY());
			stage.setWidth(maximized.getWidth());
			stage.setHeight(maximized.getHeight());
			button.setGraphic(FontAwesome.createIcon(FontAwesome.RESIZE_SMALL));
		}
	}

}