package com.mercuryirc.client.protocol.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Set;
import java.util.TreeSet;

public class User implements Target, Comparable<User> {

	private Server server;
	private StringProperty name = new SimpleStringProperty("");
	private String userName;
	private String realName;
	private String host;

	private Set<String> channels;

	public User(Server server, String name, String userName, String realName, String host) {
		this.server = server;
		this.name.set(name);
		this.userName = userName;
		this.realName = realName;
		this.host = host;
		this.channels = new TreeSet<>();
	}

	public User(Server server, String name, String userName, String realName) {
		this(server, name, userName, realName, null);
	}

	public User(Server server, String name) {
		this(server, name, null, null, null);
	}

	public Server getServer() {
		return server;
	}

	public StringProperty getNameProperty() {
		return name;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void addChannel(String ch) {
		channels.add(ch);
	}

	public boolean isInChannel(String ch) {
		return channels.contains(ch);
	}

	public String[] getChannels() {
		return channels.toArray(new String[channels.size()]);
	}

	public void removeChannel(String ch) {
		channels.remove(ch);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof User)) {
			return false;
		}
		User user = (User) o;
		return user.getServer().equals(server) && user.getNameProperty().equals(name);
	}

	@Override
	public int compareTo(User o) {
		return RankComparator.INSTANCE.compare(name.get(), o.getName());
	}
}