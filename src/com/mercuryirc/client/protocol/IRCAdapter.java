package com.mercuryirc.client.protocol;

import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Message;
import com.mercuryirc.client.protocol.model.User;

/**
 * Contains empty implementations of IRCCallback methods
 * so that clients can pick and choose which methods they would like
 * to implement without adhering to the whole IRCCallback interface.
 */
public class IRCAdapter implements IRCCallback {
	public void onMessage(Message msg) { }
	public void onChannelJoin(User user, Channel chan) { }
}
