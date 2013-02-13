package com.mercuryirc.client.protocol.network.commands;

import com.mercuryirc.client.protocol.IRCTools;
import com.mercuryirc.client.protocol.model.Message;
import com.mercuryirc.client.protocol.network.Connection;

public class MessageReceived implements Connection.CommandHandler {
	public boolean appliesTo(String command, String line) {
		return command.equals("PRIVMSG") || command.equals("NOTICE");
	}

	public void process(String line, String[] parts, Connection conn) {
		String from = IRCTools.parseTarget(parts[0]);
		String cmd = parts[1];
		String to = parts[2];

		// add 2 for the " :"
		String text = line.substring(line.indexOf(to) + to.length() + 2);
		Message message = new Message(Message.Kind.valueOf(cmd.toUpperCase()), from, to, text);

		conn.getListener().onMessage(message);
	}
}
