package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import javafx.scene.layout.BorderPane;

public class ContentPane extends BorderPane {
	private ApplicationPane appPane;

	private TopicPane topicPane;
	private MessageList messageList;
	private InputPane inputPane;
	private UserPane userPane;

	public ContentPane(ApplicationPane ap) {
		getStylesheets().add(Mercury.class.getResource("./res/css/ContentPane.css").toExternalForm());

		appPane = ap;

		topicPane = new TopicPane();
		messageList = new MessageList();
		inputPane = new InputPane();
		userPane = new UserPane();

		setTop(topicPane);
		setCenter(messageList);
		setRight(userPane);
	}
}
