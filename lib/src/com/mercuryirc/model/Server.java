package com.mercuryirc.model;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.util.HashSet;
import java.util.Set;

public class Server implements Entity {

	private final ReadOnlyStringWrapper name = new ReadOnlyStringWrapper();
	private final String host;
	private final int port;
	private final String password;
	private final boolean ssl;

	/**
	 * all the users that the IRC client knows about
	 */
	private Set<User> users = new HashSet<>();
	private Set<Channel> channels = new HashSet<>();

	public Server(String name, String host, int port, String password, boolean ssl) {
		this.name.set(name);
		this.host = host;
		this.port = port;
		this.password = password;
		this.ssl = ssl;
	}

	public Server getServer() {
		return this;
	}

	public String getName() {
		return name.get();
	}

	public ReadOnlyStringProperty getNameProperty() {
		return name.getReadOnlyProperty();
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getPassword() {
		return password	;
	}

	public boolean isSsl() {
		return ssl;
	}

	public void addLocalUser(User user) {
		users.add(user);
	}

	/**
	 * @see Server#getUser(String, boolean)
	 */
	public User getUser(String nick) {
		return getUser(nick, true);
	}

	/**
	 * @return the User object associated with the given nickname. If the client
	 *         does not know about a User with this nick and 'create' is true, a new
	 *         User object is created and returned, otherwise, null is returned.
	 */
	public User getUser(String nick, boolean create) {
		User user = null;
		for (User _user : users) {
			if (_user.getName().equalsIgnoreCase(nick)) {
				user = _user;
				break;
			}
		}
		if (user == null && create) {
			user = new User(this, nick);
			users.add(user);
		}
		return user;
	}

	public void removeUser(User user) {

		for(Channel channel : user.getChannels()) {
			channel.removeUser(user);
		}
		users.remove(user);
	}

	public Channel getChannel(String name) {
		return getChannel(name, true);
	}

	public Channel getChannel(String name, boolean create) {
		Channel channel = null;
		for (Channel _channel : channels) {
			if (_channel.getName().equalsIgnoreCase(name)) {
				channel = _channel;
				break;
			}
		}
		if (channel == null && create) {
			channel = new Channel(this, name);
			channels.add(channel);
		}
		return channel;
	}

}