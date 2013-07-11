package com.mercuryirc.client.ui;

import com.mercuryirc.model.Channel;
import com.mercuryirc.model.Entity;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TopicPane extends HBox {

	private final ApplicationPane appPane;
	private final Tab tab;

	private Label topicLabel;

	public TopicPane(ApplicationPane appPane, Tab tab) {
		this.appPane = appPane;
		this.tab = tab;
		Entity entity = tab.getEntity();
		getStyleClass().add("light-pane");
		setId(entity instanceof Channel ? "topic-pane-channel" : "topic-pane");
		setMinHeight(85);
		setMaxHeight(85);
		setHgrow(this, Priority.ALWAYS);
		topicLabel = new Label();
		topicLabel.getStyleClass().add("topic");

		VBox left = new VBox();

		String name = entity.getName();
		Label nameLabel = new Label(entity instanceof Channel ? name.substring(1) : name);
		nameLabel.getStyleClass().add("name");
		String type = entity.getClass().getSimpleName().toLowerCase();
		Label typeLabel = new Label(type);
		typeLabel.getStyleClass().add("type");

		left.getChildren().addAll(typeLabel, nameLabel, topicLabel);

		getChildren().add(left);

		HBox right = new HBox();
		// ... part button, etc
	}

	public void setTopic(String topic) {
		topicLabel.setText(topic);
	}
}
