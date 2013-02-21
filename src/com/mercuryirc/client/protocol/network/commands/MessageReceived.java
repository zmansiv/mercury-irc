package com.mercuryirc.client.protocol.network.commands;

import com.mercuryirc.client.protocol.misc.IrcUtils;
import com.mercuryirc.client.protocol.model.Message;
import com.mercuryirc.client.protocol.network.Connection;

public class MessageReceived implements Connection.CommandHandler {

	public boolean applies(Connection connection, String command, String line) {
		return command.equals("PRIVMSG") || command.equals("NOTICE");
	}

	public void process(Connection connection, String line, String[] parts) {
		String from = IrcUtils.parseSource(parts[0]);
		String cmd = parts[1];
		String to = parts[2];

		String text = line.substring(line.indexOf(':', 1) + 1);

		if(text.length() > 0 && text.charAt(0) == '\u0001') {
			String ctcp = text.substring(1, text.indexOf('\u0001', 1));
			connection.getInputCallback().onCTCP(connection, from, ctcp);
		} else {
			Message message = new Message(Message.Type.valueOf(cmd.toUpperCase()), from, to, text);
			connection.getInputCallback().onMessage(connection, message);
		}
	}

}
