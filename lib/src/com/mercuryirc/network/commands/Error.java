package com.mercuryirc.network.commands;

import com.mercuryirc.network.Connection;

public class Error implements Connection.CommandHandler {

	@Override
	public boolean applies(Connection connection, String command, String line) {
		return command.equals("ERROR");
	}

	@Override
	public void process(Connection connection, String line, String[] parts) {
		connection.getCallback().onError(connection, line.substring(7));
	}

}