package com.mercuryirc.model;


import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlySetProperty;
import javafx.beans.property.ReadOnlySetWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class User implements Entity {

	private ReadOnlyObjectWrapper<Server> server = new ReadOnlyObjectWrapper<>();
	private ReadOnlyStringWrapper name = new ReadOnlyStringWrapper();
	private ReadOnlyStringWrapper userName = new ReadOnlyStringWrapper();
	private ReadOnlyStringWrapper realName = new ReadOnlyStringWrapper();
	private ReadOnlyStringWrapper host = new ReadOnlyStringWrapper();

	private Map<Channel, ReadOnlySetWrapper<Mode.Type>> channelRanks = new HashMap<>();

	private ReadOnlyStringWrapper nickservPassword = new ReadOnlyStringWrapper();
	private ReadOnlyListWrapper<String> autojoinChannels = new ReadOnlyListWrapper<>(FXCollections.observableList(new ArrayList<String>()));

	public User(Server server, String name, String userName, String realName, String host) {
		this.server.set(server);
		this.name.set(name);
		setUserName(userName);
		this.realName.set(realName);
		this.host.set(host);
	}

	public User(Server server, String name, String userName, String realName) {
		this(server, name, userName, realName, null);
	}

	public User(Server server, String name) {
		this(server, name, null, null, null);
	}

	public Server getServer() {
		return server.get();
	}

	public ReadOnlyObjectProperty<Server> getServerProperty() {
		return server;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public ReadOnlyStringProperty getNameProperty() {
		return name.getReadOnlyProperty();
	}

	public String getUserName() {
		return userName.get();
	}

	public void setUserName(String userName) {
		this.userName.set(userName != null && userName.toCharArray()[0] == '~' ? userName.substring(1) : userName);
	}

	public ReadOnlyStringProperty getUserNameProperty() {
		return userName;
	}

	public String getRealName() {
		return realName.get();
	}

	public void setRealName(String realName) {
		this.realName.set(realName);
	}

	public ReadOnlyStringProperty getRealNameProperty() {
		return realName;
	}

	public String getHost() {
		return host.get();
	}

	public void setHost(String host) {
		this.host.set(host);
	}

	public ReadOnlyStringProperty getHostProperty() {
		return host;
	}

	public Set<Channel> getChannels() {
		return channelRanks.keySet();
	}

	public void addChannel(Channel channel) {
		channelRanks.put(channel, new ReadOnlySetWrapper<>(FXCollections.observableSet(new TreeSet<>(RANK_COMPARATOR))));
	}

	public void removeChannel(Channel channel) {
		channelRanks.remove(channel);
	}

	public Mode.Type getChannelRank(Channel channel) {
		Set<Mode.Type> ranks = channelRanks.get(channel);
		if (ranks.size() > 0) {
			return ranks.iterator().next();
		} else {
			return null;
		}
	}

	public void addChannelRank(Channel channel, Mode.Type rank) {
		if (!channelRanks.containsKey(channel)) {
			addChannel(channel);
		}
		channelRanks.get(channel).add(rank);
	}

	public void removeChannelRank(Channel channel, Mode.Type rank) {
		if (channelRanks.containsKey(channel)) {
			channelRanks.get(channel).remove(rank);
		}
	}

	public ReadOnlySetProperty<Mode.Type> getChannelRanksProperty(Channel channel) {
		return channelRanks.get(channel);
	}

	public String getNickservPassword() {
		return nickservPassword.get();
	}

	public void setNickservPassword(String nickservPassword) {
		this.nickservPassword.set(nickservPassword);
	}

	public ReadOnlyStringProperty getNickservPasswordProperty() {
		return nickservPassword.getReadOnlyProperty();
	}

	public List<String> getAutojoinChannels() {
		return autojoinChannels.get();
	}

	public void setAutojoinChannels(String... autojoinChannels) {
		this.autojoinChannels.setAll(autojoinChannels);
	}

	public ReadOnlyListProperty<String> getAutojoinChannelsProperty() {
		return autojoinChannels.getReadOnlyProperty();
	}

	public static final Comparator<Mode.Type> RANK_COMPARATOR = new Comparator<Mode.Type>() {
		@Override
		public int compare(Mode.Type o1, Mode.Type o2) {
			if (o1 == null) {
				if (o2 == null) {
					return 0;
				}
				return -1;
			}
			if (o2 == null) {
				return 1;
			}
			if (o1.ordinal() > o2.ordinal()) {
				return 1;
			} else if (o1.ordinal() < o2.ordinal()) {
				return -1;
			} else {
				return 0;
			}
		}
	};

	@Override
	public boolean equals(Object o) {
		if (super.equals(o)) {
			return true;
		}
		if (o instanceof User) {
			User user = (User) o;
			return user.getServer().equals(getServer()) && user.getName().equalsIgnoreCase(getName());
		}
		return false;
	}

	@Override
	public String toString() {
		return getName();
	}

}