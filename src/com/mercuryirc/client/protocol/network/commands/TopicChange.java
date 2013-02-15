package com.mercuryirc.client.protocol.network.commands;

import com.mercuryirc.client.protocol.misc.IrcUtils;
import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.network.Connection;

public class TopicChange implements Connection.CommandHandler {

	public boolean applies(Connection connection, String command, String line) {
		return command.equals("TOPIC");
	}

	public void process(Connection connection, String line, String[] parts) {
		String who = IrcUtils.parseTarget(parts[0]);
		Channel channel = connection.getServer().getChannel(parts[2]);

		String topic = line.substring(line.indexOf(':', 2) + 1);
		channel.setTopic(topic);
		channel.setTopicTimestamp(IrcUtils.getTimestamp());

		connection.getCallback().onTopicChange(connection, channel, who);
	}
}