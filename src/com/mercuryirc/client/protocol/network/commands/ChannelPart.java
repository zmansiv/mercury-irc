package com.mercuryirc.client.protocol.network.commands;

import com.mercuryirc.client.protocol.misc.IrcUtils;
import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Server;
import com.mercuryirc.client.protocol.model.User;
import com.mercuryirc.client.protocol.network.Connection;

public class ChannelPart implements Connection.CommandHandler {

	public boolean applies(Connection connection, String command, String line) {
		return command.equals("PART");
	}

	public void process(Connection connection, String line, String[] parts) {
		String chName = parts[2];

		Server srv = connection.getServer();

		String nick = IrcUtils.parseSource(parts[0]);
		User user = srv.getUser(nick);
		Channel channel = srv.getChannel(chName);

		user.removeChannel(chName);
		channel.removeNicks(nick);

		String reason = null;
		if(parts.length > 3 && parts[3].length() > 1)
			reason = line.substring(line.indexOf(':', 1) + 1);

		connection.getInputCallback().onChannelPart(connection, channel, user, reason);
	}

}
