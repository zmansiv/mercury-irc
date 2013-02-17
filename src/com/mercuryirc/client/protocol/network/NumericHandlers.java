package com.mercuryirc.client.protocol.network;

import com.mercuryirc.client.protocol.network.numerics.*;

import java.util.ArrayList;
import java.util.List;

public class NumericHandlers {

	public static final List<Connection.NumericHandler> list = new ArrayList<Connection.NumericHandler>();

	static {
		list.add(new ChannelNickList());
		list.add(new ChannelTopic());
		list.add(new TopicTimestamp());
		list.add(new EndOfNamesList());
		list.add(new Welcome());
		list.add(new NickInUse());
		list.add(new BanList());
	}

}
