package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Mode;
import com.mercuryirc.client.protocol.model.Server;
import com.mercuryirc.client.protocol.model.User;
import com.mercuryirc.client.protocol.network.Connection;
import com.mercuryirc.client.protocol.network.callback.IrcCallback;
import com.mercuryirc.client.ui.model.Message;
import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Set;

public class ApplicationPane extends BorderPane {

	private final TabPane tabPane;
	private final MessagePane messagePane;

	public ApplicationPane() {
		getStylesheets().add(Mercury.class.getResource("./res/css/ApplicationPane.css").toExternalForm());

		VBox.setVgrow(this, Priority.ALWAYS);

		tabPane = new TabPane(this);
		messagePane = new MessagePane(this);

		setLeft(tabPane);
		setRight(messagePane);
	}
}