package com.mercuryirc.client.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class TopicPane extends HBox {

	private final ApplicationPane appPane;

	private Label topicLabel;

	public TopicPane(ApplicationPane appPane) {
		this.appPane = appPane;
		getStyleClass().add("light-pane");
		setId("topic-pane");
		setMinHeight(85);
		setMaxHeight(85);
		setHgrow(this, Priority.ALWAYS);
		topicLabel = new Label("TopicPane");
		getChildren().add(topicLabel);
	}

	public void setTopic(String topic) {
		topicLabel.setText(topic);
	}
}
