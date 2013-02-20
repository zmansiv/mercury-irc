package com.mercuryirc.client;

import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Message;
import com.mercuryirc.client.protocol.model.Mode;
import com.mercuryirc.client.protocol.model.Target;
import com.mercuryirc.client.protocol.model.User;
import com.mercuryirc.client.protocol.network.Connection;
import com.mercuryirc.client.protocol.network.callback.InputCallback;
import com.mercuryirc.client.ui.ApplicationPane;
import com.mercuryirc.client.ui.Tab;
import com.mercuryirc.client.ui.model.MessageRow;
import javafx.application.Platform;

import java.util.List;
import java.util.Set;

public class InputCallbackImpl implements InputCallback {

	private ApplicationPane appPane;

	public InputCallbackImpl(ApplicationPane appPane) {
		this.appPane = appPane;
	}

	public void onConnect(final Connection connection) {
		connection.joinChannel("#mercury");
	}

	@Override
	public void onMessage(final Connection connection, final Message message) {
		Platform.runLater(new Runnable() {
			public void run() {
				Target target = connection.resolveTarget(message.getTarget());
				MessageRow row = new MessageRow(connection, message);
				Tab tab;
				if (message.getType() == Message.Type.NOTICE) {
					tab = appPane.getTabPane().getSelected();
				} else {
					if (message.getTarget().startsWith("#")) {
						tab = appPane.getTabPane().get(target);
					} else {
						tab = appPane.getTabPane().get(connection.resolveTarget(message.getSource()));
					}
				}
				tab.getContentPane().getMessagePane().addRow(row);
			}
		});
	}

	@Override
	public void onChannelJoin(final Connection connection, final Channel channel, final User user) {
		Platform.runLater(new Runnable() {
			public void run() {
				MessageRow row = new MessageRow(channel.getName(), user.getName() + " has joined the channel", MessageRow.Type.EVENT);
				Tab tab = appPane.getTabPane().get(channel);
				tab.getContentPane().getMessagePane().addRow(row);
				tab.getContentPane().getTopicPane().setTopic(channel.getTopic());
				if (user.equals(connection.getLocalUser())) {
					appPane.getTabPane().select(tab);
				}
			}
		});
	}

	@Override
	public void onChannelPart(final Connection connection, final Channel channel, final User user, final String reason) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				appPane.getTabPane().get(channel).getContentPane().getMessagePane().addRow(new MessageRow(channel.getName(), user.getName() + " has left the channel" + (reason == null ? "" : " (" + reason + ")"), MessageRow.Type.EVENT));
				if (user.equals(connection.getLocalUser())) {
					appPane.getTabPane().close(appPane.getTabPane().get(channel));
				}
			}
		});
	}

	@Override
	public void onUserQuit(Connection connection, final User user, final String reason) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for (Tab tab : appPane.getTabPane().getItems()) {
					if (tab.getContentPane().getUserPane().getUsers().contains(user)) {
						tab.getContentPane().getMessagePane().addRow(new MessageRow(tab.getTarget().getName(), user.getName() + " has quit" + (reason == null ? "" : " (" + reason + ")"), MessageRow.Type.EVENT));
					}
				}
			}
		});
	}

	@Override
	public void onChannelNickList(Connection connection, final Channel channel, final Set<String> nicks) {
		Platform.runLater(new Runnable() {
			public void run() {
				appPane.getTabPane().get(channel).getContentPane().getUserPane().setUsers(nicks);
			}
		});
	}

	@Override
	public void onTopicChange(Connection connection, final Channel channel, final String who, final String topic) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				appPane.getTabPane().get(channel).getContentPane().getMessagePane().addRow(new MessageRow(channel.getName(), who + " has set the topic to: " + topic, MessageRow.Type.EVENT));
				appPane.getTabPane().get(channel).getContentPane().getTopicPane().setTopic(topic);
			}
		});
	}

	@Override
	public void onUserNickChange(final Connection connection, final User user, final String oldNick) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for (Tab tab : appPane.getTabPane().getItems()) {
					for (User _user : tab.getContentPane().getUserPane().getUsers()) {
						if (_user.getName().equals(oldNick) && _user.getServer().equals(user.getServer())) {
							tab.getContentPane().getMessagePane().addRow(new MessageRow(tab.getTarget().getName(), oldNick + " has changed their name to " + user.getName(), MessageRow.Type.EVENT));
							break;
						}
					}
				}
			}
		});
	}

	@Override
	public void onUserKick(final Connection connection, final Channel channel, final User user, final String reason) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				appPane.getTabPane().get(channel).getContentPane().getMessagePane().addRow(new MessageRow(channel.getName(), user.getName() + " has been kicked out of the channel" + (reason == null ? "" : " (" + reason + ")"), MessageRow.Type.EVENT));
				if (user.equals(connection.getLocalUser())) {
					appPane.getTabPane().close(appPane.getTabPane().get(channel));
				}
			}
		});
	}

	@Override
	public void onChannelModeList(Connection connection, Channel channel, Mode.Type type, List<Mode> list) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

}