package com.mercuryirc.client.protocol.network.commands;

import com.mercuryirc.client.protocol.network.Connection;

public class Ping implements Connection.CommandHandler {

	public boolean applies(Connection connection, String command, String line) {
		return command.equals("PING");
	}

	public void process(Connection connection, String line, String[] parts) {
		connection.writeLine("PONG :" + line.substring(6));
	}

}
