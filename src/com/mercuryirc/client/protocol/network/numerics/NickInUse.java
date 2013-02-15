package com.mercuryirc.client.protocol.network.numerics;

import com.mercuryirc.client.protocol.network.Connection;

public class NickInUse implements Connection.NumericHandler {
	public boolean applies(Connection connection, int numeric) {
		// only run during registration. If a user tries to change nicks when
		// they're already connected, they can view and deal with the
		// "Nickname already in use" message without our intervention.
		return numeric == 433 && !connection.isRegistered();
	}

	public void process(Connection connection, String line, String[] parts) {
		String nick = connection.getLocalUser().getName() + "_";
		connection.getLocalUser().setName(nick);

		connection.writeLine("NICK " + nick);
	}
}
