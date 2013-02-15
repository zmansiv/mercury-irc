package com.mercuryirc.client.ui;

import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Server;
import com.mercuryirc.client.protocol.model.User;
import com.mercuryirc.client.protocol.network.Connection;
import com.mercuryirc.client.protocol.network.callback.IrcCallback;
import com.mercuryirc.client.ui.model.Message;
import javafx.application.Platform;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class ApplicationPane extends SplitPane {

	private final TargetPanel targetPanel;

	public ApplicationPane() {
		try {
			getStylesheets().add(new File("./res/css/ApplicationPane.css").toURI().toURL().toExternalForm());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		setPrefHeight(692);
		VBox.setVgrow(this, Priority.ALWAYS);
		targetPanel = new TargetPanel(this);
		getItems().addAll(targetPanel, targetPanel.getRootItem().getValue().getUserPanel(), targetPanel.getRootItem().getValue().getMessagePanel());
		setDividerPositions(0D, 0D);
		Server svr = new Server("Rizon", "irc.rizon.net", 6697, true);
		User me = new User("Test|Mercury", "test_user", "MercuryIRC");
		IrcHandler callback = new IrcHandler();
		Connection conn = new Connection(svr, me, callback);
		conn.setAcceptAllSSLCerts(true);
		conn.setExceptionHandler(callback);
		conn.connect();
	}

	public TargetPanel getTargetPanel() {
		return targetPanel;
	}

	private class IrcHandler implements Connection.ExceptionHandler, IrcCallback {

		@Override
		public void onConnect(Connection connection) {
			connection.joinChannel("#mercury");
		}

		@Override
		public void onException(Connection connection, Exception e) {
			e.printStackTrace();
		}

		@Override
		public void onMessage(final Connection connection, final com.mercuryirc.client.protocol.model.Message message) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					targetPanel.addMessage(connection, new Message(message));
				}
			});
		}

		@Override
		public void onChannelJoin(final Connection connection, final Channel channel, User user) {
			System.out.println(user.getName() + " has joined " + channel.getName());
			if (connection.isLocalUser(user.getName())) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						targetPanel.select(targetPanel.addTarget(connection, channel));
					}
				});
			}
		}

		@Override
		public void onChannelNickList(final Connection connection, final Channel channel, final Set<String> nicks) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					targetPanel.setNicks(connection, channel, nicks);
				}
			});
		}
	}
}