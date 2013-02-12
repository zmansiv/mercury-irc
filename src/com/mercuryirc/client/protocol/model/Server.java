package com.mercuryirc.client.protocol.model;

import java.util.HashMap;
import java.util.Map;

public class Server {
	private String host;
	private int port;
	private boolean ssl;

	/** all the users that the IRC client knows about */
	private Map<String, User> users;
	private Map<String, Channel> channels;

	public Server(String host, int port, boolean ssl) {
		this.host = host;
		this.port = port;
		this.ssl = ssl;

		this.users = new HashMap<String, User>();
		this.channels = new HashMap<String, Channel>();
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public boolean isSsl() {
		return ssl;
	}

	public User getUser(String nick) {
		User u = users.get(nick);
		if(u == null) {
			u = new User(nick);
			users.put(nick, u);
		}
		return u;
	}

	public Channel getChannel(String name) {
		Channel c = channels.get(name);
		if(c == null) {
			c = new Channel(name);
			channels.put(name, c);
		}
		return c;
	}
}
