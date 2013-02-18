package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ApplicationPane extends BorderPane {

	private final TabPane tabPane;
	private final ContentPane contentPane;

	public ApplicationPane() {
		getStylesheets().add(Mercury.class.getResource("./res/css/ApplicationPane.css").toExternalForm());

		VBox.setVgrow(this, Priority.ALWAYS);

		tabPane = new TabPane(this);
		contentPane = new ContentPane(this);

		setLeft(tabPane);
		setCenter(contentPane);
	}
}