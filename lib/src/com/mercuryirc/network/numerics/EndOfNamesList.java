package com.mercuryirc.network.numerics;

import com.mercuryirc.model.Channel;
import com.mercuryirc.model.Server;
import com.mercuryirc.network.Connection;

/**
 * Only send the list of nicks to the callback after the server has finished
 * sending all of them:
 *
 * :chat.server 366 Test|Mercury #Mercury :End of /NAMES list.
 */
public class EndOfNamesList implements Connection.NumericHandler {
	public boolean applies(Connection connection, int numeric) {
		return numeric == 366;
	}

	public void process(Connection connection, String line, String[] parts) {
		Server srv = connection.getServer();
		Channel channel = srv.getChannel(parts[3]);

		connection.getCallback().onNickList(connection, channel, channel.getUsers());
	}
}
