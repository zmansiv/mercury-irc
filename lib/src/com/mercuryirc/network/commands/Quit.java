package com.mercuryirc.network.commands;

import com.mercuryirc.misc.IrcUtils;
import com.mercuryirc.model.Server;
import com.mercuryirc.model.User;
import com.mercuryirc.network.Connection;

public class Quit implements Connection.CommandHandler {

	public boolean applies(Connection connection, String command, String line) {
		return command.equals("QUIT");
	}

	public void process(Connection connection, String line, String[] parts) {
		//>> :mlaux_!user@99-37-202-122.lightspeed.dllstx.sbcglobal.net PART #oculr
		//		>> :mlaux_!user@99-37-202-122.lightspeed.dllstx.sbcglobal.net JOIN :#oculr
		//		>> :mlaux_!user@99-37-202-122.lightspeed.dllstx.sbcglobal.net QUIT :Quit:

		final String name = IrcUtils.parseSource(parts[0]);

		final Server svr = connection.getServer();
		final User user = svr.getUser(name);

		String reason = null;
		if(parts.length > 2 && parts[2].length() > 1) {
			reason = line.substring(line.indexOf(':', 1) + 1);
		}

		connection.getCallback().onQuit(connection, user, reason);
		svr.removeUser(user);
	}

}