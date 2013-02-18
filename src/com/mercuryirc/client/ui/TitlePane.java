package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import com.mercuryirc.client.ui.misc.FontAwesome;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class TitlePane extends StackPane {

	private final Button resizeButton;
	private double dragX = 0D;
	private double dragY = 0D;
	private double unmaximizedX = 0D;
	private double unmaximizedY = 0D;
	private double unmaximizedWidth = 1000D;
	private double unmaximizedHeight = 650D;

	public TitlePane(final Stage stage) {
		getStylesheets().add(Mercury.class.getResource("./res/css/TitlePane.css").toExternalForm());
		setId("title-bar");
		setMinHeight(50);
		setMaxHeight(50);
		Label titleLabel = new Label("mercury");
		titleLabel.setId("title");
		HBox buttonBox = new HBox(15);
		buttonBox.setPadding(new Insets(0, 15, 0, 0));
		buttonBox.setAlignment(Pos.CENTER_RIGHT);
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		Button minimizeButton = FontAwesome.createIconButton(FontAwesome.MINUS, false);
		minimizeButton.setId("minimize-button");
		minimizeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				stage.setIconified(true);
			}
		});
		resizeButton = FontAwesome.createIconButton(FontAwesome.RESIZE_FULL, false);
		resizeButton.setId("resize-button");
		resizeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				toggleMaximization(stage);
			}
		});
		Button closeButton = FontAwesome.createIconButton(FontAwesome.REMOVE, false);
		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				stage.close();
			}
		});
		buttonBox.getChildren().addAll(spacer, minimizeButton, resizeButton, closeButton);
		getChildren().addAll(titleLabel, buttonBox);
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().ordinal() == MouseButton.PRIMARY.ordinal() && mouseEvent.getClickCount() == 2) { //Double click
					toggleMaximization(stage);
				}
			}
		});
		setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown()) {
					dragX = event.getSceneX();
					dragY = event.getSceneY();
				}
			}
		});
		setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown()) {
					Rectangle2D maximized = Screen.getPrimary().getVisualBounds();
					if (stage.getWidth() == maximized.getWidth() && stage.getHeight() == maximized.getHeight()) {
						int sceneClickX = (int) (event.getSceneX() / getWidth() * unmaximizedWidth);
						stage.setX(event.getScreenX() - sceneClickX);
						stage.setY(event.getScreenY() - event.getSceneY());
						stage.setWidth(unmaximizedWidth);
						stage.setHeight(unmaximizedHeight);
						dragX = event.getScreenX() - stage.getX();
						dragY = event.getScreenY() - stage.getY();
						resizeButton.setGraphic(FontAwesome.createIcon(FontAwesome.RESIZE_FULL));
					} else {
						stage.setX(event.getScreenX() - dragX);
						stage.setY(event.getScreenY() - dragY);
					}
				}
			}
		});
	}

	private void toggleMaximization(Stage stage) {
		Screen screen = null;
		for (Screen _screen : Screen.getScreens()) {
			if (_screen.getBounds().contains(stage.getX(), stage.getY())) {
				screen = _screen;
				break;
			}
		}
		if (screen == null) {
			screen = Screen.getPrimary();
		}
		Rectangle2D maximized = screen.getVisualBounds();
		if (stage.getWidth() == maximized.getWidth() && stage.getHeight() == maximized.getHeight()) {
			stage.setX(unmaximizedX);
			stage.setY(unmaximizedY);
			stage.setWidth(unmaximizedWidth);
			stage.setHeight(unmaximizedHeight);
			resizeButton.setGraphic(FontAwesome.createIcon(FontAwesome.RESIZE_FULL));
		} else {
			unmaximizedX = stage.getX();
			unmaximizedY = stage.getY();
			unmaximizedWidth = stage.getWidth();
			unmaximizedHeight = stage.getHeight();
			stage.setX(maximized.getMinX());
			stage.setY(maximized.getMinY());
			stage.setWidth(maximized.getWidth());
			stage.setHeight(maximized.getHeight());
			resizeButton.setGraphic(FontAwesome.createIcon(FontAwesome.RESIZE_SMALL));
		}
	}

}