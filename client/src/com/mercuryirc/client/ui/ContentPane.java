package com.mercuryirc.client.ui;

import com.mercuryirc.network.Connection;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class ContentPane extends VBox {

	private final Connection connection;

	private final TopicPane topicPane;
	private final MessagePane messagePane;
	private final UserPane userPane;

	public ContentPane(Connection connection, ApplicationPane appPane, Tab tab) {
		this.connection = connection;
		VBox.setVgrow(this, Priority.ALWAYS);
		HBox.setHgrow(this, Priority.ALWAYS);
		HBox box = new HBox();
		VBox.setVgrow(box, Priority.ALWAYS);
		HBox.setHgrow(box, Priority.ALWAYS);
		box.getChildren().addAll(messagePane = new MessagePane(appPane, tab, connection), userPane = new UserPane(appPane, tab.getEntity()));
		getChildren().addAll(topicPane = new TopicPane(appPane, tab), box);
	}

	public Connection getConnection() {
		return connection;
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