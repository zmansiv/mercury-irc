package com.mercuryirc.client.ui;

import javafx.scene.layout.GridPane;

public class ContentPane extends GridPane {

	private TopicPane topicPane;
	private MessagePane messagePane;
	private UserPane userPane;

	public ContentPane(ApplicationPane appPane) {
		add(topicPane = new TopicPane(appPane), 0, 0, 2, 1);
		add(messagePane = new MessagePane(appPane), 0, 1);
		add(userPane = new UserPane(appPane), 1, 1);
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