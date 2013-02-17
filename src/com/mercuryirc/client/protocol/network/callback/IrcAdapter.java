package com.mercuryirc.client.protocol.network.callback;

import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Message;
import com.mercuryirc.client.protocol.model.Mode;
import com.mercuryirc.client.protocol.model.User;
import com.mercuryirc.client.protocol.network.Connection;

import java.util.List;
import java.util.Set;

/**
 * Contains empty implementations of IrcCallback methods
 * so that clients can pick and choose which methods they would like
 * to implement without adhering to the whole IrcCallback interface.
 */
public class IrcAdapter implements IrcCallback {

	public void onConnect(Connection connection) { }

	public void onMessage(Connection connection, Message message) {}

	public void onChannelJoin(Connection connection, Channel channel, User user) {}

	public void onChannelPart(Connection connection, Channel channel, User user, String reason) { }

	public void onUserQuit(Connection connection, User user, String reason) { }

	public void onChannelNickList(Connection connection, Channel channel, Set<String> nicks) {}

	public void onTopicChange(Connection connection, Channel channel, String who) { }

	public void onUserNickChange(Connection connection, User user, String oldNick) { }

	public void onUserKick(Connection connection, Channel channel, User user, String reason) { }

	public void onChannelList(Connection connection, Channel channel, Mode.Type type, List<Mode> list) { }
}
