package com.mercuryirc.model;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.ReadOnlyLongWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;

import java.util.LinkedList;
import java.util.List;

public class Channel implements Entity {

	private ReadOnlyObjectWrapper<Server> server = new ReadOnlyObjectWrapper<>();
	private ReadOnlyStringWrapper name = new ReadOnlyStringWrapper();

	private ReadOnlyStringWrapper topic = new ReadOnlyStringWrapper();
	private ReadOnlyObjectWrapper<User> topicAuthor = new ReadOnlyObjectWrapper<>();
	private ReadOnlyLongWrapper topicTimestamp = new ReadOnlyLongWrapper();

	private ReadOnlyListWrapper<User> users = new ReadOnlyListWrapper<>(FXCollections.observableList(new LinkedList<User>()));

	private ReadOnlyListWrapper<Mode> bans = new ReadOnlyListWrapper<>(FXCollections.observableList(new LinkedList<Mode>()));
	private ReadOnlyListWrapper<Mode> invites = new ReadOnlyListWrapper<>(FXCollections.observableList(new LinkedList<Mode>()));
	private ReadOnlyListWrapper<Mode> excepts = new ReadOnlyListWrapper<>(FXCollections.observableList(new LinkedList<Mode>()));

	public Channel(Server server, String name) {
		this.server.set(server);
		this.name.set(name);
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

	public ReadOnlyStringProperty getNameProperty() {
		return name;
	}

	public String getTopic() {
		return topic.get();
	}

	public ReadOnlyStringProperty getTopicProperty() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic.set(topic);
	}

	public List<User> getUsers() {
		return users;
	}

	public void addUser(final User user) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (!users.contains(user)) {
					users.add(user);
				}
			}
		});
	}

	public void removeUser(final User user) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				users.remove(user);
			}
		});
	}

	public ReadOnlyListProperty<User> getUsersProperty() {
		return users;
	}

	public User getTopicAuthor() {
		return topicAuthor.get();
	}

	public void setTopicAuthor(User author) {
		topicAuthor.set(author);
	}

	public ReadOnlyObjectProperty<User> getTopicAuthorProperty() {
		return topicAuthor.getReadOnlyProperty();
	}

	public long getTopicTimestamp() {
		return topicTimestamp.get();
	}

	public void setTopicTimestamp(long ts) {
		topicTimestamp.set(ts);
	}

	public ReadOnlyLongProperty getTopicTimestampProperty() {
		return topicTimestamp;
	}

	public void clearData() {
		topic.set(null);
		users.clear();
	}

	public List<Mode> getBans() {
		return bans.get();
	}

	public ReadOnlyListProperty<Mode> getBansProperty() {
		return bans;
	}

	public List<Mode> getInvites() {
		return invites.get();
	}

	public ReadOnlyListProperty<Mode> getInvitesProperty() {
		return invites;
	}

	public List<Mode> getExcepts() {
		return excepts.get();
	}

	public ReadOnlyListProperty<Mode> getExceptsProperty() {
		return excepts;
	}

	@Override
	public boolean equals(Object o) {
		if (super.equals(o)) {
			return true;
		}
		if (o instanceof Channel) {
			Channel channel = (Channel) o;
			return channel.getServer().equals(getServer()) && channel.getName().equalsIgnoreCase(getName());
		}
		return false;
	}

}