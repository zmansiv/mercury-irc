package com.mercuryirc.client.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class TopicPane extends BorderPane {
	public TopicPane() {
		setId("topic-pane");
		setCenter(new Label("TopicPane"));
	}
}
