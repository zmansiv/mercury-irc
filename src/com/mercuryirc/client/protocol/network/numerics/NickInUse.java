package com.mercuryirc.client.protocol.network.numerics;

import com.mercuryirc.client.protocol.network.Connection;

public class NickInUse implements Connection.NumericHandler {
	public boolean applies(Connection connection, int numeric) {
		return numeric == 433;
	}

	public void process(Connection connection, String line, String[] parts) {
		String nick = connection.getLocalUser().getName() + "_";
		connection.getLocalUser().setName(nick);

		connection.writeLine("NICK " + nick);
	}
}
