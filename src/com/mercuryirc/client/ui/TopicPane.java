package com.mercuryirc.client.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TopicPane extends HBox {

	private final ApplicationPane appPane;
	private final Tab tab;

	private Label lblTopic;

	public TopicPane(ApplicationPane appPane, Tab tab) {
		this.appPane = appPane;
		this.tab = tab;
		getStyleClass().add("light-pane");
		setId("topic-pane");
		setMinHeight(85);
		setMaxHeight(85);
		setHgrow(this, Priority.ALWAYS);
		lblTopic = new Label();
		lblTopic.getStyleClass().add("topic");

		VBox left = new VBox();

		String name = tab.getTarget().getName();
		Label lblName = new Label(name);
		lblName.getStyleClass().add("name");
		String type = name.startsWith("#") ? "channel" : "private message";
		Label lblType = new Label(type);
		lblType.getStyleClass().add("type");

		left.getChildren().addAll(lblType, lblName, lblTopic);

		getChildren().add(left);

		HBox right = new HBox();
		// ... part button, etc
	}

	public void setTopic(String topic) {
		lblTopic.setText(topic);
	}
}
