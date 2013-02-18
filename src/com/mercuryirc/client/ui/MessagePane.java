package com.mercuryirc.client.ui;

import javafx.scene.layout.BorderPane;

public class MessagePane extends BorderPane {
	private ApplicationPane appPane;

	private TopicPane topicPane;
	private MessageList messageList;
	private InputPane inputPane;
	private UserPane userPane;

	public MessagePane(ApplicationPane ap) {
		appPane = ap;

		topicPane = new TopicPane();
		messageList = new MessageList();
		inputPane = new InputPane();
		userPane = new UserPane();
	}
}
