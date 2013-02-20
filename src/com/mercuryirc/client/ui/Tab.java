package com.mercuryirc.client.ui;

import com.mercuryirc.client.protocol.model.Target;

public class Tab {
	private Target target;

	private TopicPane topicPane;
	private MessagePane messagePane;
	private UserPane userPane;

	public Tab(ApplicationPane appPane, Target target) {
		this.target = target;

		this.topicPane = new TopicPane(appPane);
		this.messagePane = new MessagePane(appPane);
		this.userPane = new UserPane(appPane);
	}

	public Target getTarget() {
		return target;
	}

	public TopicPane getTopicPane() {
		return topicPane;
	}

	public MessagePane getMessagePane() {
		return messagePane;
	}

	public UserPane getUserPane() {
		return userPane;
	}
}
