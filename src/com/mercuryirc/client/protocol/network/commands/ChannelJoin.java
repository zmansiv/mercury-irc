package com.mercuryirc.client.protocol.network.commands;

import com.mercuryirc.client.protocol.misc.IrcUtils;
import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Server;
import com.mercuryirc.client.protocol.model.User;
import com.mercuryirc.client.protocol.network.Connection;

public class ChannelJoin implements Connection.CommandHandler {

	public boolean applies(Connection connection, String command, String line) {
		return command.equals("JOIN");
	}

	public void process(Connection connection, String line, String[] parts) {
		String chName = parts[2];

		// some irc servers send :#channel, some just send #channel
		if(chName.startsWith(":"))
			chName = chName.substring(1);

		Server srv = connection.getServer();

		String nick = IrcUtils.parseTarget(parts[0]);
		User user = srv.getUser(nick);
		Channel channel = srv.getChannel(chName);

		// update state
		user.addChannel(chName);
		channel.addNicks(nick);

		// if the person who joined the channel was us...
		if (nick.equalsIgnoreCase(connection.getLocalUser().getName())) {
			// clear any saved state about the channel so that
			// the server can fill us in again
			srv.getChannel(chName).clearData();
		}

		// get info on the hostmasks
		connection.writeLine("WHO " + chName);

		connection.getCallback().onChannelJoin(connection, channel, user);
	}

}
