package com.mercuryirc.network.commands;

import com.mercuryirc.network.Connection;

public class Ping implements Connection.CommandHandler {

	public boolean applies(Connection connection, String command, String line) {
		return command.equals("PING");
	}

	public void process(Connection connection, String line, String[] parts) {
		connection.pong(line.substring(6));
	}

}