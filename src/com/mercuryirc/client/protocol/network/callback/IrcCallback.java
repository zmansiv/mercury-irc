package com.mercuryirc.client.protocol.network.callback;

import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Message;
import com.mercuryirc.client.protocol.model.User;
import com.mercuryirc.client.protocol.network.Connection;

import java.util.Set;

public interface IrcCallback {

	public void onConnect(Connection connection);

	public void onMessage(Connection connection, Message message);

	public void onChannelJoin(Connection connection, Channel channel, User user);

	public void onChannelNickList(Connection connection, Channel channel, Set<String> nicks);

}
