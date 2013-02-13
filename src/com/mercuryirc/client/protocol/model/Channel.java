package com.mercuryirc.client.protocol.model;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Channel {
	private String name;

	private String topic;
	private long topicTimestamp;

	private Set<String> users = new TreeSet<String>(new RankComparator());

	public Channel(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Set<String> getNames() {
		return users;
	}

	public void addNames(List<String> nickList) {
		this.users.addAll(nickList);
		System.out.println("Users on " + name + ": " + this.users);
	}

	public void clear() {
		topic = null;
		users.clear();
	}

	public void setTopicTimestamp(long ts) {
		topicTimestamp = ts;
	}
}
