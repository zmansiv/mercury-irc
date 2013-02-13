package com.mercuryirc.client.protocol.network.commands;

import com.mercuryirc.client.protocol.network.Connection;

public class ChannelJoin implements Connection.CommandHandler {
	public boolean appliesTo(String command, String line) {
		return command.equals("JOIN");
	}

	public void process(String line, String[] parts, Connection conn) {
		String chan = line.substring(line.lastIndexOf(':') + 1);
		String user = parts[0].substring(1, parts[0].indexOf('!'));

		// if the person who joined the channel was us...
		if(user.equalsIgnoreCase(conn.getLocalUser().getNick())) {
			// clear any saved state about the channel so that
			// the server can fill us in again
			conn.getServer().getChannel(chan).clear();


		}
	}
}
