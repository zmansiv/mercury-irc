package com.mercuryirc.client.ui;

import com.mercuryirc.network.Connection;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class ContentPane extends VBox {

	private final Connection connection;

	private final TopicPane topicPane;
	private final MessagePane messagePane;
	private final InputPane inputPane;
	private final UserPane userPane;

	public ContentPane(Connection connection, ApplicationPane appPane, Tab tab) {
		this.connection = connection;
		VBox.setVgrow(this, Priority.ALWAYS);
		HBox.setHgrow(this, Priority.ALWAYS);
		VBox innerBox = new VBox();
		VBox.setVgrow(innerBox, Priority.ALWAYS);
		HBox.setHgrow(innerBox, Priority.ALWAYS);
		innerBox.getChildren().addAll(messagePane = new MessagePane(appPane, tab, connection), inputPane = new InputPane(appPane, connection));
		HBox outerBox = new HBox();
		VBox.setVgrow(outerBox, Priority.ALWAYS);
		HBox.setHgrow(outerBox, Priority.ALWAYS);
		outerBox.getChildren().addAll(innerBox, userPane = new UserPane(appPane, tab.getEntity()));
		getChildren().addAll(topicPane = new TopicPane(appPane, tab), outerBox);
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

	public InputPane getInputPane() {
		return inputPane;
	}

	public UserPane getUserPane() {
		return userPane;
	}

}