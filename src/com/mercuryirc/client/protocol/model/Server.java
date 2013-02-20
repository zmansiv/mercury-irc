package com.mercuryirc.client.protocol.model;

import com.mercuryirc.client.protocol.misc.IrcUtils;

import java.util.HashMap;
import java.util.Map;

public class Server implements Target {

	private final String name;
	private final String host;
	private final int port;
	private final boolean ssl;

	/**
	 * all the users that the IRC client knows about
	 */
	private Map<String, User> users;
	private Map<String, Channel> channels;

	public Server(String name, String host, int port, boolean ssl) {
		this.name = name;
		this.host = host;
		this.port = port;
		this.ssl = ssl;

		this.users = new HashMap<>();
		this.channels = new HashMap<>();
	}

	public String getName() {
		return name;
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

	/**
	 * @see Server#getUser(String, boolean)
	 */
	public User getUser(String nick) {
		return getUser(nick, true);
	}

	/**
	 * @return the User object associated with the given nickname. If the client
	 * does not know about a User with this nick and 'create' is true, a new
	 * User object is created and returned, otherwise, null is returned.
	 */
	public User getUser(String nick, boolean create) {
		if(IrcUtils.isRank(nick.charAt(0)))
			nick = nick.substring(1);

		User u = users.get(nick);
		if (u == null && create) {
			u = new User(this, nick);
			users.put(nick, u);
		}
		return u;
	}

	public void removeUser(String nick) {
		if(IrcUtils.isRank(nick.charAt(0)))
			nick = nick.substring(1);

		users.remove(nick);
	}

	public Channel getChannel(String name) {
		return getChannel(name, true);
	}

	public Channel getChannel(String name, boolean create) {
		Channel c = channels.get(name);
		if (c == null && create) {
			c = new Channel(name);
			channels.put(name, c);
		}
		return c;
	}

}
