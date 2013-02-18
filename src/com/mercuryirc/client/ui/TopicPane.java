package com.mercuryirc.client.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TopicPane extends HBox {

	private final ApplicationPane appPane;

	public TopicPane(ApplicationPane appPane) {
		this.appPane = appPane;
		getStyleClass().add("light-pane");
		setId("topic-pane");
		setMinHeight(85);
		setMaxHeight(85);
		getChildren().add(new Label("TopicPane"));
	}

}
