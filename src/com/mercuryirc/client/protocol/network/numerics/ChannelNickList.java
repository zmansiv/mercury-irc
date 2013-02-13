package com.mercuryirc.client.protocol.network.numerics;

import com.mercuryirc.client.protocol.model.Server;
import com.mercuryirc.client.protocol.network.Connection;

import java.util.Arrays;

public class ChannelNickList implements Connection.NumericHandler {
	public boolean appliesTo(int numeric) {
		return numeric == 353;
	}

	@Override
	public void process(String line, String[] parts, Connection conn) {
		String chan = parts[4];
		String[] names = line.substring(line.lastIndexOf(':') + 1).split(" ");

		Server srv = conn.getServer();
		srv.getChannel(chan).addNames(Arrays.asList(names));
	}
}
