package com.mercuryirc.client.protocol.model;


import com.sun.xml.internal.ws.config.management.policy.ManagementPolicyValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class User implements Target {

	private String name;
	private String userName;
	private String realName;
	private String host;

	private Set<String> channels;

	private Map<String, String> properties;

	public User(String name, String userName, String realName, String host) {
		this.name = name;
		this.userName = userName;
		this.realName = realName;
		this.host = host;
		this.channels = new TreeSet<>();
		this.properties = new HashMap<>();
	}

	public User(String name, String userName, String realName) {
		this(name, userName, realName, null);
	}

	public User(String name) {
		this(name, null, null, null);
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
}
