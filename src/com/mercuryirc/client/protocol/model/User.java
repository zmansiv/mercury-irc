package com.mercuryirc.client.protocol.model;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class User implements Target {

	private Server server;
	private String name;
	private String userName;
	private String realName;
	private String host;

	private Set<String> channels;

	private Map<String, String> properties;

	public User(Server server, String name, String userName, String realName, String host) {
		this.server = server;
		this.name = name;
		this.userName = userName;
		this.realName = realName;
		this.host = host;
		this.channels = new TreeSet<>();
		this.properties = new HashMap<>();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return user.getServer().equals(server) && user.getName().equals(name);
	}

}