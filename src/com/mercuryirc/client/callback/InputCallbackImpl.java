package com.mercuryirc.client.callback;

import com.mercuryirc.client.Mercury;
import com.mercuryirc.client.ui.ApplicationPane;
import com.mercuryirc.client.ui.Tab;
import com.mercuryirc.client.ui.TabPane;
import com.mercuryirc.client.misc.Tray;
import com.mercuryirc.client.ui.model.MessageRow;
import com.mercuryirc.model.Channel;
import com.mercuryirc.model.Entity;
import com.mercuryirc.model.Message;
import com.mercuryirc.model.Mode;
import com.mercuryirc.model.User;
import com.mercuryirc.network.Connection;
import com.mercuryirc.network.callback.InputCallback;
import javafx.application.Platform;

import java.awt.Toolkit;
import java.util.List;
import java.util.Set;

public class InputCallbackImpl implements InputCallback {

	private ApplicationPane appPane;

	public InputCallbackImpl(ApplicationPane appPane) {
		this.appPane = appPane;
	}

	public void onConnect(final Connection connection) {
		User local = connection.getLocalUser();
		if (local.getNickservPassword() != null) {
			connection.privmsg(new Message(local, connection.getServer().getUser("NickServ"), "identify " + local.getNickservPassword()), true);
		}
		if (local.getAutojoinChannels() != null) {
			try {
				Thread.sleep(750);
			} catch (InterruptedException e) {
			}
			for (String channel : local.getAutojoinChannels()) {
				connection.join(channel);
			}
		}
	}

	@Override
	public void onPrivMsg(final Connection connection, final Message message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				boolean highlight = message.getMessage().toLowerCase().contains(connection.getLocalUser().getName().toLowerCase());
				appPane.getTabPane().addTargetedMessage(connection, message, highlight ? MessageRow.Type.HIGHLIGHT : MessageRow.Type.PRIVMSG);
				if (highlight && !Mercury.getStage().isFocused()) {
					String title = "Highlighted by " + message.getSource().getName();
					Tray.notify(title, message.getMessage(), true);
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});
	}

	@Override
	public void onNotice(final Connection connection, final Message message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				appPane.getTabPane().addUntargetedMessage(connection, message, MessageRow.Type.NOTICE);
				if (!Mercury.getStage().isFocused()) {
					Tray.notify("Notice from " + message.getSource(), message.getMessage(), true);
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});
	}

	@Override
	public void onCtcp(final Connection connection, final Message message) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String ctcp = message.getMessage();
				if (ctcp.startsWith("ACTION")) {
					Message _message = new Message(message.getSource(), message.getTarget(), ctcp.substring(7));
					appPane.getTabPane().addTargetedMessage(connection, _message, MessageRow.Type.EVENT);
				} else {
					Message _message = new Message(message.getSource(), message.getTarget(), ctcp);
					appPane.getTabPane().addUntargetedMessage(connection, _message, MessageRow.Type.CTCP);
					if (ctcp.equals("VERSION")) {
						String arch = System.getProperty("os.arch");
						if (arch.equals("amd64")) {
							arch = "x64";
						}
						String sysInfo = System.getProperty("os.name") + " (" + arch + " "
								+ Runtime.getRuntime().availableProcessors() + "-core)";
						connection.ctcp(message.getSource(), "VERSION MercuryIRC // " + sysInfo);
					}
				}
			}
		});
	}

	@Override
	public void onChannelJoin(final Connection connection, final Channel channel, final User user) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Message message = new Message(user, channel, "has joined the channel");
				appPane.getTabPane().addTargetedMessage(connection, message, MessageRow.Type.JOIN);
			}
		});
	}

	@Override
	public void onChannelPart(final Connection connection, final Channel channel, final User user, final String reason) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Message message = new Message(user, channel, "has left the channel (" + reason + ")");
				appPane.getTabPane().addTargetedMessage(connection, message, MessageRow.Type.PART);
				if (user.equals(connection.getLocalUser())) {
					appPane.getTabPane().close(appPane.getTabPane().get(connection, channel), false);
				}
			}
		});
	}

	@Override
	public void onUserQuit(final Connection connection, final User user, final String reason) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Message message = new Message(user, null, "has quit (" + reason + ")");
				appPane.getTabPane().addUserStatusMessage(user, message, MessageRow.Type.PART, null);
				if (user.equals(connection.getLocalUser())) {
					appPane.getTabPane().close(appPane.getTabPane().get(connection, connection.getServer()), false);
				}
			}
		});
	}

	@Override
	public void onChannelNickList(final Connection connection, final Channel channel, final List<User> users) {
	}

	@Override
	public void onTopicChange(final Connection connection, final Channel channel, final User source, final String topic) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Message message = new Message(source, channel, "has set the topic: " + topic);
				appPane.getTabPane().addTargetedMessage(connection, message, MessageRow.Type.EVENT);
				appPane.getTabPane().get(connection, channel).getContentPane().getTopicPane().setTopic(topic);
			}
		});
	}

	@Override
	public void onUserNickChange(final Connection connection, final User user, final String oldNick) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Message message = new Message(null, null, oldNick + " is now known as " + user.getName());
				appPane.getTabPane().addUserStatusMessage(user, message, MessageRow.Type.EVENT, new TabPane.TabAction() {
					@Override
					public void process(Tab tab) {
						tab.getContentPane().getUserPane().sort();
					}
				});
				if (user.equals(connection.getLocalUser())) {
					appPane.getContentPane().getMessagePane().getInputPane().setNick(user.getName());
				}
			}
		});
	}

	@Override
	public void onUserKick(final Connection connection, final Channel channel, final User user, final String reason) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Message message = new Message(user, channel, "has been kicked from the channel (" + reason + ")");
				appPane.getTabPane().addTargetedMessage(connection, message, MessageRow.Type.PART);
			}
		});
	}

	@Override
	public void onChannelModeList(Connection connection, Channel channel, Mode.Type type, List<Mode> list) {
		Message message = new Message(null, null, "Channel " + type.toString() + " list:");
		appPane.getTabPane().addTargetedMessage(connection, message, MessageRow.Type.EVENT);
		for (Mode mode : list) {
			Message message2 = new Message(null, null, ((User) mode.getTarget()).getHost() + " by " + mode.getSource().getHost());
			appPane.getTabPane().addTargetedMessage(connection, message2, MessageRow.Type.EVENT);
		}
	}

	@Override
	public void onError(final Connection connection, final String error) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Message message = new Message(null, null, error);
				appPane.getTabPane().addUntargetedMessage(connection, message, MessageRow.Type.ERROR);
				Toolkit.getDefaultToolkit().beep();
			}
		});
	}

	@Override
	public void onUnknownCommand(final Connection connection, final String command) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Message message = new Message(null, null, "Unknown command: " + command);
				appPane.getTabPane().addUntargetedMessage(connection, message, MessageRow.Type.ERROR);
			}
		});
	}

	@Override
	public void onModeChange(final Connection connection, final Entity target, final Set<Mode> modes, final boolean add) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				for (Mode mode : modes) {
					Message message = new Message(mode.getSource(), target, (add ? "" : "un") + "sets " + mode.getType().toString() + " on " + mode.getTarget().getName() + (mode.getTarget().equals(target) ? "" : " in " + target.getName()));
					if (target instanceof Channel) {
						appPane.getTabPane().addTargetedMessage(connection, message, MessageRow.Type.EVENT);
					} else {
						appPane.getTabPane().addUntargetedMessage(connection, message, MessageRow.Type.EVENT);
					}
				}
			}
		});
	}

}