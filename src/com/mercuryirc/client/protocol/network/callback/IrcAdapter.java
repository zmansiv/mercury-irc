package com.mercuryirc.client.protocol.network.callback;

import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Message;
import com.mercuryirc.client.protocol.model.User;
import com.mercuryirc.client.protocol.network.Connection;

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

	public void onChannelNickList(Connection connection, Channel channel, Set<String> nicks) {}

}
