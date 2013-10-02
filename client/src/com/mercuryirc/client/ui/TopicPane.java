package com.mercuryirc.client.ui;

import com.mercuryirc.client.ui.misc.FontAwesome;
import com.mercuryirc.model.Channel;
import com.mercuryirc.model.Entity;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
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
		final Entity entity = tab.getEntity();
		setId("topic-pane");
		getStyleClass().add("light-pane");
		setMinHeight(85);
		setMaxHeight(85);
		setHgrow(this, Priority.ALWAYS);

		VBox left = new VBox();
		HBox.setHgrow(left, Priority.ALWAYS);
		left.setId(entity instanceof Channel ? "left-topic-box-channel" : "left-topic-box");
		String type = entity.getClass().getSimpleName().toLowerCase();
		Label typeLabel = new Label(type);
		typeLabel.getStyleClass().add("type");
		String name = entity.getName();
		Label nameLabel = new Label(entity instanceof Channel ? name.substring(1) : name);
		nameLabel.getStyleClass().add("name");
		topicLabel = new Label();
		topicLabel.getStyleClass().add("topic");

		left.getChildren().addAll(typeLabel, nameLabel, topicLabel);

		VBox right = new VBox();
		right.setId("right-topic-box");
		Button partButton = FontAwesome.createIconButton(FontAwesome.SIGN_OUT, "", true, "red", "-fx-padding: 3px 4px 7px 6px;");
		partButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				TopicPane.this.appPane.getTabPane().close(TopicPane.this.tab);
			}
		});
		right.getChildren().add(partButton);

		getChildren().addAll(left, right);
	}

	public void setTopic(String topic) {
		topicLabel.setText(topic);
	}
}
