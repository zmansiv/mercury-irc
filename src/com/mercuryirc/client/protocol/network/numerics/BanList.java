package com.mercuryirc.client.protocol.network.numerics;

import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Mode;
import com.mercuryirc.client.protocol.network.Connection;
import com.mercuryirc.client.protocol.network.callback.IrcCallback;

public class BanList implements Connection.NumericHandler {
	private static final int[] numerics = { 367, 368, 346, 347, 348, 349 };

	public boolean applies(Connection connection, int numeric) {
		for(int n : numerics)
			if(numeric == n)
				return true;
		return false;
	}

	public void process(Connection connection, String line, String[] parts) {
		Channel ch = connection.getServer().getChannel(parts[3]);
		IrcCallback cb = connection.getCallback();

		int num = Integer.parseInt(parts[1]);

		if(num == 367 || num == 346 || num == 348) {
			Mode m = new Mode(parts[4], parts[5], Long.parseLong(parts[6]));

			switch(Integer.parseInt(parts[1])) {
				case 367: ch.getBans().add(m);    break;
				case 346: ch.getInvites().add(m); break;
				case 348: ch.getExcepts().add(m); break;
			}
		} else switch(Integer.parseInt(parts[1])) {
			case 368: cb.onChannelModeList(connection, ch, Mode.Type.BAN, ch.getBans());       break;
			case 347: cb.onChannelModeList(connection, ch, Mode.Type.INVITE, ch.getInvites()); break;
			case 349: cb.onChannelModeList(connection, ch, Mode.Type.EXCEPT, ch.getExcepts()); break;
		}
	}

}
