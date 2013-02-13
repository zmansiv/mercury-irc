package com.mercuryirc.client.ui;

import com.mercuryirc.client.misc.Message;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;

public class ApplicationPane extends SplitPane {

	public ApplicationPane() {
		try {
			getStylesheets().add(new File("./res/css/ApplicationPane.css").toURI().toURL().toExternalForm());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		setPrefHeight(692);
		VBox.setVgrow(this, Priority.ALWAYS);
		NetworkPanel networkPanel = new NetworkPanel();
		UserPanel userPanel = new UserPanel();
		MessagePanel messagePanel = new MessagePanel();
		getItems().addAll(networkPanel, userPanel, messagePanel);
		setDividerPositions(0D, 0D);
		for (int i = 0; i < 50; i++) {
			messagePanel.messages().add(new Message("Paradigm", "testing lol1", Message.MessageType.ME));
			messagePanel.messages().add(new Message("Tekk", "no u", Message.MessageType.OTHER));
			messagePanel.messages().add(new Message("Tekk", "Paradigm!!!", Message.MessageType.HIGHLIGHT));
			messagePanel.messages().add(new Message("ChanServ", "sets mode +b Paradigm*!*@*", Message.MessageType.EVENT));
		}
	}

}