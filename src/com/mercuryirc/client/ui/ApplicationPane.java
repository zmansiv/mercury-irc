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

	public ApplicationPane() {
		getStylesheets().add(Mercury.class.getResource("./res/css/ApplicationPane.css").toExternalForm());
		VBox.setVgrow(this, Priority.ALWAYS);
		tabPane = new TabPane(this);
		setLeft(tabPane);
		setRight(new ChatPane());
		Server svr = new Server("Rizon", "irc.rizon.net", 6697, true);
		User me = new User("Test|Mercury", "test_user", "MercuryIRC");
		IrcHandler callback = new IrcHandler();
		Connection conn = new Connection(svr, me, callback);
		conn.setAcceptAllSSLCerts(true);
		conn.setExceptionHandler(callback);
		//conn.connect();
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
					tabPane.addMessage(connection, new Message(connection, message));
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
						tabPane.getSelectionModel().select(tabPane.addTarget(connection, channel));
					}

				});
			}
		}

		@Override
		public void onChannelNickList(final Connection connection, final Channel channel, final Set<String> nicks) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					tabPane.addNicks(connection, channel, nicks);
				}

			});
		}

		public void onChannelPart(Connection connection, Channel channel, User user, String reason) {
			System.out.println(user.getName() + " has left " + channel.getName() + " (" + reason + ")");
		}

		@Override
		public void onUserQuit(Connection connection, User user, String reason) {
			System.out.println(user.getName() + " has quit (" + reason + ")");
		}

		@Override
		public void onTopicChange(Connection connection, Channel channel, String who) {
			System.out.println(who + " changed the topic of " + channel.getName() + " to " + channel.getTopic());
		}

		@Override
		public void onUserNickChange(Connection connection, User user, String oldNick) {
			System.out.println(oldNick + " is now known as " + user.getName());
		}

		@Override
		public void onUserKick(Connection connection, Channel channel, User user, String reason) {
			System.out.println(user.getName() + " was kicked from " + channel.getName() + " (" + reason + ")");
		}

		@Override
		public void onChannelModeList(Connection connection, Channel channel, Mode.Type type, List<Mode> list) {
		}

	}

}