package com.mercuryirc.network.numerics;

import com.mercuryirc.network.Connection;
import javafx.application.Platform;

public class NickInUse implements Connection.NumericHandler {

	public boolean applies(Connection connection, int numeric) {
		// only run during registration. If a user tries to change nicks when
		// they're already connected, they can view and deal with the
		// "Nickname already in use" message without our intervention.
		return numeric == 433 && !connection.isRegistered();
	}

	public void process(final Connection connection, String line, String[] parts) {
		final String nick = connection.getLocalUser().getName() + "_";
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				connection.getLocalUser().setName(nick);
			}
		});

		connection.getLocalUser().setName(nick);
		connection.nick(nick);
	}

}