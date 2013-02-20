package com.mercuryirc.client.protocol.network.commands;

import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.User;
import com.mercuryirc.client.protocol.network.Connection;

public class Kick implements Connection.CommandHandler {
	public boolean applies(Connection connection, String command, String line) {
		return command.equals("KICK");
	}

	@Override
	public void process(Connection connection, String line, String[] parts) {
		//:prefix KICK channel target :reason
		Channel channel = connection.getServer().getChannel(parts[2]);
		channel.removeNicks(parts[3]);

		String reason = null;
		if(parts.length > 4 && parts[4].length() > 1)
			reason = line.substring(line.indexOf(':', 1) + 1);

		User user = connection.getServer().getUser(parts[3]);
		connection.getInputCallback().onUserKick(connection, channel, user, reason);
	}
}
