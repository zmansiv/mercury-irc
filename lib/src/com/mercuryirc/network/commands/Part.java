package com.mercuryirc.network.commands;

import com.mercuryirc.misc.IrcUtils;
import com.mercuryirc.model.Channel;
import com.mercuryirc.model.Server;
import com.mercuryirc.model.User;
import com.mercuryirc.network.Connection;

public class Part implements Connection.CommandHandler {

	public boolean applies(Connection connection, String command, String line) {
		return command.equals("PART");
	}

	public void process(Connection connection, String line, String[] parts) {
		String chName = parts[2];

		Server srv = connection.getServer();

		String nick = IrcUtils.parseSource(parts[0]);
		User user = srv.getUser(nick);
		Channel channel = srv.getChannel(chName);

		user.removeChannel(channel);
		channel.removeUser(user);
		if (user.equals(connection.getLocalUser())) {
			channel.clearData();
		}

		String reason = null;
		if(parts.length > 3 && parts[3].length() > 1)
			reason = line.substring(line.indexOf(':', 1) + 1);

		connection.getCallback().onPart(connection, channel, user, reason);
	}

}
