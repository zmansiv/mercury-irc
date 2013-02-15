package com.mercuryirc.client.protocol.network.commands;

import com.mercuryirc.client.protocol.misc.IrcUtils;
import com.mercuryirc.client.protocol.model.User;
import com.mercuryirc.client.protocol.network.Connection;

public class NickChange implements Connection.CommandHandler {

	@Override
	public boolean applies(Connection connection, String command, String line) {
		return command.equals("NICK");
	}

	@Override
	public void process(Connection connection, String line, String[] parts) {
		String oldNick = IrcUtils.parseTarget(parts[0]);
		User user = connection.getServer().getUser(oldNick);

		user.setName(parts[2].substring(1));
		user.setHost(parts[0].substring(parts[0].indexOf('@') + 1));
		user.setUserName(line.substring(line.indexOf('!') + 1, line.indexOf('@')));

		connection.getCallback().onUserNickChange(connection, user, oldNick);
	}

}