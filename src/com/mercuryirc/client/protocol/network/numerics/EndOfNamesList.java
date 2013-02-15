package com.mercuryirc.client.protocol.network.numerics;

import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Server;
import com.mercuryirc.client.protocol.network.Connection;

/**
 * Only send the list of nicks to the callback after the server has finished
 * sending all of them:
 *
 * :irc.server 366 Test|Mercury #Mercury :End of /NAMES list.
 */
public class EndOfNamesList implements Connection.NumericHandler {
	public boolean applies(Connection connection, int numeric) {
		return numeric == 366;
	}

	public void process(Connection connection, String line, String[] parts) {
		Server srv = connection.getServer();
		Channel channel = srv.getChannel(parts[3]);

		System.out.println("Users on channel " + channel.getName() + ": " + channel.getNicks());
		connection.getCallback().onChannelNickList(connection, channel, channel.getNicks());
	}
}
