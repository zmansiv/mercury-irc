package com.mercuryirc.client.protocol.network;

import com.mercuryirc.client.protocol.network.commands.*;

import java.util.ArrayList;
import java.util.List;

public class CommandHandlers {

	public static final List<Connection.CommandHandler> list = new ArrayList<Connection.CommandHandler>(3);

	static {
		list.add(new Ping());
		list.add(new ChannelJoin());
		list.add(new MessageReceived());
		list.add(new ChannelPart());
		list.add(new Quit());
		list.add(new TopicChange());
		list.add(new NickChange());
		list.add(new Kick());
	}

}
