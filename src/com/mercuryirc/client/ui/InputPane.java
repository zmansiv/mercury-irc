package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import com.mercuryirc.client.ui.misc.FontAwesome;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class InputPane extends HBox {
	private Label nickLabel;
	private TextField inputText;
	private Button send;

	public InputPane() {
		setId("input-pane");

		setSpacing(0);
		setPadding(new Insets(10, 5, 10, 5));

		Region space = new Region();
		HBox.setHgrow(space, Priority.ALWAYS);

		nickLabel = new Label("test");
		inputText = new TextField();
		send = FontAwesome.createIconButton(FontAwesome.REPLY, "send");

		getChildren().addAll(nickLabel, inputText, space, send);
	}
}
