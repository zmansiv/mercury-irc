package com.mercuryirc.client.ui;

import com.mercuryirc.client.ui.misc.FontAwesome;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class InputPane extends HBox {

	private Label nickLabel;

	public InputPane(ApplicationPane appPane) {
		getStyleClass().add("dark-pane");
		setId("input-pane");
		setAlignment(Pos.CENTER);
		setMinHeight(65);
		setMaxHeight(65);
		setSpacing(0);
		setPadding(new Insets(10, 5, 10, 5));
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		nickLabel = new Label("nick");
		TextField inputField = new TextField();
		Button sendButton = FontAwesome.createIconButton(FontAwesome.REPLY, "send", "blue");
		getChildren().addAll(nickLabel, inputField, spacer, sendButton);
	}

	public Label getNickLabel() {
		return nickLabel;
	}

}