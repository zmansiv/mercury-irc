package com.mercuryirc.client.protocol.model;

import java.util.*;

public class Channel implements Target {

	private final String name;

	private String topic;
	private long topicTimestamp;

	private Set<String> nicks = new TreeSet<>();

	private List<Mode> bans = new ArrayList<>();
	private List<Mode> invites = new ArrayList<>();
	private List<Mode> excepts = new ArrayList<>();


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

	public void addNicks(String... nickList) {
		nicks.addAll(Arrays.asList(nickList));
	}

	public void removeNicks(String... nickList) {
		nicks.removeAll(Arrays.asList(nickList));
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

	public List<Mode> getBans() {
		return bans;
	}

	public List<Mode> getInvites() {
		return invites;
	}

	public List<Mode> getExcepts() {
		return excepts;
	}
}
