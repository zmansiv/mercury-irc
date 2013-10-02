package com.mercuryirc.network.commands;

import com.mercuryirc.model.Channel;
import com.mercuryirc.model.User;
import com.mercuryirc.network.Connection;

public class Kick implements Connection.CommandHandler {

	public boolean applies(Connection connection, String command, String line) {
		return command.equals("KICK");
	}

	@Override
	public void process(Connection connection, String line, String[] parts) {
		//:prefix KICK channel target :reason
		Channel channel = connection.getServer().getChannel(parts[2]);
		User user = connection.getServer().getUser(parts[3]);

		channel.removeUser(user);
		user.removeChannel(channel);

		if (user.equals(connection.getLocalUser())) {
			channel.clearData();
		}

		String reason = null;
		if(parts.length > 4 && parts[4].length() > 1)
			reason = line.substring(line.indexOf(':', 1) + 1);

		connection.getCallback().onKick(connection, channel, user, reason);
	}

}