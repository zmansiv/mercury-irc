package com.mercuryirc.client.protocol.network.numerics;

import com.mercuryirc.client.protocol.network.Connection;

public class TopicTimestamp implements Connection.NumericHandler {

	@Override
	public boolean applies(Connection connection, int numeric) {
		return numeric == 333;
	}

	@Override
	public void process(Connection connection, String line, String[] parts) {
		String chan = parts[3];
		long ts = Long.parseLong(parts[5]);
		connection.getServer().getChannel(chan).setTopicTimestamp(ts);
	}

}
