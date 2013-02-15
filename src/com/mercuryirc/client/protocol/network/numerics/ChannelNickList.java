package com.mercuryirc.client.protocol.network.numerics;

import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Server;
import com.mercuryirc.client.protocol.network.Connection;

import java.util.Arrays;

public class ChannelNickList implements Connection.NumericHandler {

	public boolean applies(Connection connection, int numeric) {
		return numeric == 353;
	}

	@Override
	public void process(Connection connection, String line, String[] parts) {
		String[] names = line.substring(line.lastIndexOf(':') + 1).split(" ");

		Server srv = connection.getServer();

		// parts[4] = channel name
		srv.getChannel(parts[4]).addNicks(names);

		// don't notify callback yet, since more 353 numerics may be coming
		// wait for the EndOfNamesList
	}

}
