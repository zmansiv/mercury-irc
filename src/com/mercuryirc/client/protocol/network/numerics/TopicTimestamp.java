package com.mercuryirc.client.protocol.network.numerics;

import com.mercuryirc.client.protocol.network.Connection;

public class TopicTimestamp implements Connection.NumericHandler {
	@Override
	public boolean appliesTo(int numeric) {
		return numeric == 333;
	}

	@Override
	public void process(String line, String[] parts, Connection conn) {
		String chan = parts[3];
		long ts = Long.parseLong(parts[5]);

		conn.getServer().getChannel(chan).setTopicTimestamp(ts);
	}
}
