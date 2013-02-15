package com.mercuryirc.client.protocol.network.commands;

import com.mercuryirc.client.protocol.model.Server;
import com.mercuryirc.client.protocol.network.Connection;

public class ChannelJoin implements Connection.CommandHandler {

	public boolean applies(Connection connection, String command, String line) {
		return command.equals("JOIN");
	}

	public void process(Connection connection, String line, String[] parts) {
		String chan = parts[2];

		// some irc servers send :#channel, some just send #channel
		if(chan.startsWith(":"))
			chan = chan.substring(1);

		String user = parts[0].substring(1, parts[0].indexOf('!'));

		Server srv = connection.getServer();

		// if the person who joined the channel was us...
		if (user.equalsIgnoreCase(connection.getLocalUser().getName())) {
			// clear any saved state about the channel so that
			// the server can fill us in again
			srv.getChannel(chan).clearData();
		}

		connection.getCallback().onChannelJoin(connection, srv.getChannel(chan), srv.getUser(user));
	}

}
