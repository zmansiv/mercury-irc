package com.mercuryirc.client;

import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Message;
import com.mercuryirc.client.protocol.model.Mode;
import com.mercuryirc.client.protocol.model.User;
import com.mercuryirc.client.protocol.network.Connection;
import com.mercuryirc.client.protocol.network.callback.IrcCallback;

import java.util.List;
import java.util.Set;

public class MercuryCallback implements IrcCallback {
	@Override
	public void onConnect(Connection connection) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onMessage(Connection connection, Message message) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onChannelJoin(Connection connection, Channel channel, User user) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onChannelPart(Connection connection, Channel channel, User user, String reason) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onUserQuit(Connection connection, User user, String reason) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onChannelNickList(Connection connection, Channel channel, Set<String> nicks) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onTopicChange(Connection connection, Channel channel, String who) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onUserNickChange(Connection connection, User user, String oldNick) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onUserKick(Connection connection, Channel channel, User user, String reason) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void onChannelModeList(Connection connection, Channel channel, Mode.Type type, List<Mode> list) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
