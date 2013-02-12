package com.mercuryirc.client.protocol.network.numerics;

import com.mercuryirc.client.protocol.model.Server;
import com.mercuryirc.client.protocol.network.Connection;

import java.util.Arrays;

public class ChannelTopic implements Connection.NumericHandler {
	public boolean appliesTo(int numeric) {
		return numeric == 332;
	}

	public void process(String line, String[] parts, Connection conn) {
		String chan = parts[3];

		String look = chan + " :";
		int idx = line.indexOf(look) + look.length();
		String topic = line.substring(idx);

		Server srv = conn.getServer();
		srv.getChannel(chan).setTopic(topic);

		System.out.println("Topic for " + chan + " is " + topic);
	}
}
