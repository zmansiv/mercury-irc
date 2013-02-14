package com.mercuryirc.client.protocol.model;


public class User implements Target {

	private String name;
	private String userName;
	private String realName;
	private String host;

	public User(String name, String userName, String realName, String host) {
		this.name = name;
		this.userName = userName;
		this.realName = realName;
		this.host = host;
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

}
