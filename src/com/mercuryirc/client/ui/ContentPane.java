package com.mercuryirc.client.ui;

import javafx.scene.control.SplitPane;

import java.io.File;
import java.io.IOException;

public class ContentPane extends SplitPane {

	public ContentPane() throws IOException {
		getStylesheets().add(new File("./res/css/ContentPane.css").toURI().toURL().toExternalForm());
		setPrefHeight(692);
		ContentPanel serverPanel = new ContentPanel();
		ContentPanel userPanel = new ContentPanel();
		MessagePanel messagePanel = new MessagePanel();
		getItems().addAll(serverPanel, userPanel, messagePanel);
		setDividerPositions(0D, 0D);
	}

}