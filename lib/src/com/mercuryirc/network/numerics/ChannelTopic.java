package com.mercuryirc.network.numerics;

import com.mercuryirc.model.Server;
import com.mercuryirc.network.Connection;

public class ChannelTopic implements Connection.NumericHandler {

	public boolean applies(Connection connection, int numeric) {
		return numeric == 332;
	}

	public void process(Connection connection, String line, String[] parts) {
		String chan = parts[3];
		String look = chan + " :";
		int idx = line.indexOf(look) + look.length();
		String topic = line.substring(idx);
		Server srv = connection.getServer();
		srv.getChannel(chan).setTopic(topic);
	}

}
