package com.mercuryirc.client.protocol.model;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Channel {
	private String name;
	private String topic;
	private Set<String> users = new TreeSet<String>(new RankComparator());

	public Channel(String name) {
		this.name = name;
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

	public void addNames(List<String> users) {
		this.users.addAll(users);
	}
}
