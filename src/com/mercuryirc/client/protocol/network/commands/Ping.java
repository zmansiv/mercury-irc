package com.mercuryirc.client.protocol.network.commands;

import com.mercuryirc.client.protocol.network.Connection;

public class Ping implements Connection.CommandHandler {
	public boolean appliesTo(String command, String line) {
		return command.equals("PING");
	}

	public void process(String line, String[] parts, Connection conn) {
		conn.writeLine("PONG :" + line.substring(6));
	}
}
