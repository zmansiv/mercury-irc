package com.mercuryirc.network.commands;

import com.mercuryirc.misc.IrcUtils;
import com.mercuryirc.model.Channel;
import com.mercuryirc.network.Connection;

public class Topic implements Connection.CommandHandler {

	public boolean applies(Connection connection, String command, String line) {
		return command.equals("TOPIC");
	}

	public void process(Connection connection, String line, String[] parts) {
		String who = IrcUtils.parseSource(parts[0]);
		Channel channel = connection.getServer().getChannel(parts[2]);

		String topic = line.substring(line.indexOf(':', 2) + 1);
		channel.setTopic(topic);
		channel.setTopicTimestamp(IrcUtils.getTimestamp());

		connection.getCallback().onTopic(connection, channel, connection.getServer().getUser(who), topic);
	}
}