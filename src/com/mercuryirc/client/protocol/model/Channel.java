package com.mercuryirc.client.protocol.model;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Channel implements Target {

	private final String name;

	private String topic;
	private long topicTimestamp;

	private Set<String> nicks = new TreeSet<>();

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

	public Set<String> getNicks() {
		return nicks;
	}

	public void addNicks(List<String> nickList) {
		this.nicks.addAll(nickList);
		System.out.println("Users on " + name + ": " + this.nicks);
	}

	public long getTopicTimestamp() {
		return topicTimestamp;
	}

	public void setTopicTimestamp(long ts) {
		topicTimestamp = ts;
	}

	public void clearData() {
		topic = null;
		nicks.clear();
	}

}
