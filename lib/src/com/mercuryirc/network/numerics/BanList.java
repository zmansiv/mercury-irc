package com.mercuryirc.network.numerics;

import com.mercuryirc.misc.IrcUtils;
import com.mercuryirc.model.Channel;
import com.mercuryirc.model.Mode;
import com.mercuryirc.model.Server;
import com.mercuryirc.model.User;
import com.mercuryirc.network.Connection;
import com.mercuryirc.network.callback.Callback;

public class BanList implements Connection.NumericHandler {

	private static final int[] numerics = {367, 368, 346, 347, 348, 349};

	public boolean applies(Connection connection, int numeric) {
		for (int n : numerics)
			if (numeric == n)
				return true;
		return false;
	}

	public void process(Connection connection, String line, String[] parts) {
		Server server = connection.getServer();
		Channel channel = server.getChannel(parts[3]);
		Callback callback = connection.getCallback();

		User source = server.getUser(IrcUtils.parseSource(parts[5]));
		source.setHost(parts[5]);
		User target = server.getUser(IrcUtils.parseSource(parts[4]));
		target.setHost(parts[4]);

		int num = Integer.parseInt(parts[1]);
		if (num == 367 || num == 346 || num == 348) {
			switch (Integer.parseInt(parts[1])) {
				case 367:
					channel.getBans().add(new Mode(source, target, Long.parseLong(parts[6]), Mode.Type.BAN));
					break;
				case 346:
					channel.getInvites().add(new Mode(source, target, Long.parseLong(parts[6]), Mode.Type.INVITE));
					break;
				case 348:
					channel.getExcepts().add(new Mode(source, target, Long.parseLong(parts[6]), Mode.Type.EXCEPT));
					break;
			}
		} else switch (Integer.parseInt(parts[1])) {
			case 368:
				callback.onModeList(connection, channel, Mode.Type.BAN, channel.getBans());
				break;
			case 347:
				callback.onModeList(connection, channel, Mode.Type.INVITE, channel.getInvites());
				break;
			case 349:
				callback.onModeList(connection, channel, Mode.Type.EXCEPT, channel.getExcepts());
				break;
		}
	}

}