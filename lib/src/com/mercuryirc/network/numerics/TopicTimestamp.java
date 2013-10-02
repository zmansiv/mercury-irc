package com.mercuryirc.network.numerics;

import com.mercuryirc.misc.IrcUtils;
import com.mercuryirc.model.Channel;
import com.mercuryirc.model.User;
import com.mercuryirc.network.Connection;

public class TopicTimestamp implements Connection.NumericHandler {

	@Override
	public boolean applies(Connection connection, int numeric) {
		return numeric == 333;
	}

	@Override
	public void process(Connection connection, String line, String[] parts) {
		String chan = parts[3];
		String _author = IrcUtils.parseSource(parts[4]);
		User author = connection.getServer().getUser(_author);
		long ts = Long.parseLong(parts[5]);
		Channel channel = connection.getServer().getChannel(chan);
		channel.setTopicAuthor(author);
		channel.setTopicTimestamp(ts);
		connection.getCallback().onTopic(connection, channel, author, channel.getTopic());
	}

}