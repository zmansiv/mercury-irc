package com.mercuryirc.client.protocol.model;


public class User {
	private String nick;

	private String user;
	private String host;
	private String realName;

	public User(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return nick;
	}

	public String getUser() {
		return user;
	}

	public String getHost() {
		return host;
	}

	public String getRealName() {
		return realName;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
}
