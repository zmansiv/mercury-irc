package com.mercuryirc.network;

import com.mercuryirc.network.numerics.JoinError;
import com.mercuryirc.network.numerics.BanList;
import com.mercuryirc.network.numerics.ChannelNickList;
import com.mercuryirc.network.numerics.ChannelTopic;
import com.mercuryirc.network.numerics.EndOfNamesList;
import com.mercuryirc.network.numerics.NickInUse;
import com.mercuryirc.network.numerics.TopicTimestamp;
import com.mercuryirc.network.numerics.Unknown;
import com.mercuryirc.network.numerics.Welcome;
import com.mercuryirc.network.numerics.Who;

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
		list.add(new Who());
        list.add(new JoinError());
        list.add(new Unknown());
	}

}
