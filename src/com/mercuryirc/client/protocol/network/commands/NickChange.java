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
		connection.getServer().removeUser(oldNick);

		String newNick = parts[2];
		if (newNick.startsWith(":"))
			newNick = newNick.substring(1);

		User user;
		if (connection.getLocalUser().getName().equals(oldNick)) {
			user = connection.getLocalUser();
		} else {
			user = connection.getServer().getUser(oldNick);
		}
		user.setName(newNick);
		connection.getInputCallback().onUserNickChange(connection, user, oldNick);
	}

}