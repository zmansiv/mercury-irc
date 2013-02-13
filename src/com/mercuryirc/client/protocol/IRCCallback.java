package com.mercuryirc.client.protocol;

import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Message;
import com.mercuryirc.client.protocol.model.User;

public interface IRCCallback {
	public void onMessage(Message msg);
	public void onChannelJoin(User user, Channel chan);
}
