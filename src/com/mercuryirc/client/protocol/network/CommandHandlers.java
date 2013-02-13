package com.mercuryirc.client.protocol.network;

import com.mercuryirc.client.protocol.network.commands.ChannelJoin;
import com.mercuryirc.client.protocol.network.commands.MessageReceived;
import com.mercuryirc.client.protocol.network.commands.Ping;

import java.util.ArrayList;
import java.util.List;

public class CommandHandlers {
	public static final List<Connection.CommandHandler> list = new ArrayList<Connection.CommandHandler>();

	static {
		list.add(new Ping());
		list.add(new ChannelJoin());
		list.add(new MessageReceived());
	}
}
