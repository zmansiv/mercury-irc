package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ConnectPane extends VBox {
	private TextField netName = new TextField();
	private TextField netHost = new TextField();
	private TextField netPort = new TextField();
	private TextField netPass = new TextField();

	private TextField userNick = new TextField();
	private TextField userUser = new TextField();
	private TextField userReal = new TextField();
	private TextField userPass = new TextField();

	public ConnectPane() {
		getStylesheets().add(Mercury.class.getResource("./res/css/ApplicationPane.css").toExternalForm());
		getStyleClass().add("light-pane");
		setPadding(new Insets(20, 20, 20, 20));
		setSpacing(5);

		getChildren().addAll(
				makeSection("network"),
				makeHBox("Name", "Freenode", netName),
				makeHBox("Host", "irc.freenode.net", netHost),
				makeHBox("Port", "6667", netPort),
				makeHBox("Password", "", netPass),

				makeSection("user"),
				makeHBox("Nickname", "MercuryUser", userNick),
				makeHBox("Username", "mercury", userUser),
				makeHBox("Real name", "mercury", userReal),
				makeHBox("Password", "", userPass)
		);
	}

	private Label makeSection(String title) {
		Label label = new Label(title);
		label.setFont(new Font("Open Sans", 24));
		return label;
	}

	private HBox makeHBox(String label, String prompt, TextField field) {
		field.setPromptText(prompt);

		HBox box = new HBox();
		Label labelObj = new Label(label);
		labelObj.setMinWidth(100);
		labelObj.setMaxWidth(100);
		field.setMinWidth(100);
		field.setMaxWidth(100);
		box.getChildren().addAll(labelObj, field);
		return box;
	}
}
