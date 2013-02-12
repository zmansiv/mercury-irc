package com.mercuryirc.client.protocol.network.commands;

import com.mercuryirc.client.protocol.network.Connection;

public class ChannelJoin implements Connection.CommandHandler {
	public boolean appliesTo(String command, String line) {
		return command.equals("JOIN");
	}

	public void process(String line, String[] parts, Connection conn) {
		String name = line.substring(line.lastIndexOf(':') + 1);
	}
}
