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
		String chan = parts[4];
		String[] names = line.substring(line.lastIndexOf(':') + 1).split(" ");

		Server srv = connection.getServer();
		Channel channel = srv.getChannel(chan);
		channel.addNicks(Arrays.asList(names));

		connection.getCallback().onChannelNickList(connection, channel, channel.getNicks());
	}

}
