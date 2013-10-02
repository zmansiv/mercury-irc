package com.mercuryirc.network.commands;

import com.mercuryirc.misc.IrcUtils;
import com.mercuryirc.model.User;
import com.mercuryirc.network.Connection;
import javafx.application.Platform;

public class Nick implements Connection.CommandHandler {

	@Override
	public boolean applies(Connection connection, String command, String line) {
		return command.equals("NICK");
	}

	@Override
	public void process(final Connection connection, String line, String[] parts) {
		final String oldNick = IrcUtils.parseSource(parts[0]);

		String newNick = parts[2];
		if (newNick.startsWith(":"))
			newNick = newNick.substring(1);
		final String _newNick = newNick;

		User user;
		if (connection.getLocalUser().getName().equals(oldNick)) {
			user = connection.getLocalUser();
		} else {
			user = connection.getServer().getUser(oldNick, false);
		}
		final User _user = user;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				_user.setName(_newNick);
				connection.getCallback().onNick(connection, _user, oldNick);
			}
		});
	}

}