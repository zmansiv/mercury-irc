package com.mercuryirc.network.numerics;

import com.mercuryirc.misc.IrcUtils;
import com.mercuryirc.model.Channel;
import com.mercuryirc.model.Mode;
import com.mercuryirc.model.Server;
import com.mercuryirc.model.User;
import com.mercuryirc.network.Connection;
import javafx.application.Platform;

public class ChannelNickList implements Connection.NumericHandler {

	public boolean applies(Connection connection, int numeric) {
		return numeric == 353;
	}

	@Override
	public void process(Connection connection, String line, String[] parts) {
		String[] nicks = line.substring(line.lastIndexOf(':') + 1).split(" ");

		Server srv = connection.getServer();

		// parts[4] = channel name
		final Channel channel = srv.getChannel(parts[4]);
		for (String nick : nicks) {
			Mode.Type _rank = null;
			if (IrcUtils.isRank(nick.charAt(0))) {
				_rank = IrcUtils.rankFromChar(nick.charAt(0));
				nick = nick.substring(1);
			}
			final User user = srv.getUser(nick, true);
			channel.addUser(user);
			user.addChannel(channel);
			if (_rank != null) {
				final Mode.Type rank = _rank;
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						user.addChannelRank(channel, rank);
					}
				});
			}
		}

		// don't notify callback yet, since more 353 numerics may be coming
		// wait for the EndOfNamesList
	}

}