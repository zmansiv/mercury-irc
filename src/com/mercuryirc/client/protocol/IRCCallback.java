package com.mercuryirc.client.protocol;

import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Message;
import com.mercuryirc.client.protocol.model.User;
import com.mercuryirc.client.protocol.network.Connection;

public interface IRCCallback {
	public void onMessage(Connection conn, Message msg);
	public void onChannelJoin(Connection conn, User user, Channel chan);
}
