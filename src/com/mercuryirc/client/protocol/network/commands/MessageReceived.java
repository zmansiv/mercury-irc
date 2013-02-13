package com.mercuryirc.client.protocol.network.commands;

import com.mercuryirc.client.protocol.network.Connection;

public class MessageReceived implements Connection.CommandHandler {
	public boolean appliesTo(String command, String line) {
		return command.equals("PRIVMSG") || command.equals("NOTICE");
	}

	public void process(String line, String[] parts, Connection conn) {

	}
}
