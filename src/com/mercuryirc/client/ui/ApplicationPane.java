package com.mercuryirc.client.ui;

import com.mercuryirc.client.misc.Message;
import javafx.scene.control.SplitPane;

import java.io.File;
import java.io.IOException;

public class ApplicationPane extends SplitPane {

	public ApplicationPane() throws IOException {
		getStylesheets().add(new File("./res/css/ApplicationPane.css").toURI().toURL().toExternalForm());
		setPrefHeight(692);
		ContentPanel serverPanel = new ContentPanel();
		serverPanel.setId("left-content-panel");
		ContentPanel userPanel = new ContentPanel();
		MessagePanel messagePanel = new MessagePanel();
		messagePanel.setId("right-content-panel");
		getItems().addAll(serverPanel, userPanel, messagePanel);
		setDividerPositions(0D, 0D);
		for (int i = 0; i < 50; i++) {
			messagePanel.messages().add(new Message("Paradigm", "testing lol1", Message.MessageType.ME));
			messagePanel.messages().add(new Message("Tekk", "no u", Message.MessageType.OTHER));
			messagePanel.messages().add(new Message("Tekk", "Paradigm!!!", Message.MessageType.HIGHLIGHT));
			messagePanel.messages().add(new Message("ChanServ", "sets mode +b Paradigm*!*@*", Message.MessageType.SERVER));
		}
	}

}