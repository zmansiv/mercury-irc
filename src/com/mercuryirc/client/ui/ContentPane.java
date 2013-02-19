package com.mercuryirc.client.ui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;


public class ContentPane extends GridPane {

	private ApplicationPane appPane;
	private TopicPane topicPane;
	private UserPane userPane;

	public ContentPane(ApplicationPane appPane) {
		this.appPane = appPane;

		add(topicPane = new TopicPane(appPane), 0, 0, 2, 1);
		add(new MessagePane(appPane), 0, 1);
		add(userPane = new UserPane(appPane), 1, 1);
	}

	public TopicPane getTopicPane() {
		return topicPane;
	}

	public void setMessagePane(MessagePane mp) {
		ObservableList<Node> nodes = getChildren();
		for(int k = nodes.size() - 1; k >= 0; k--) {
			Node n = nodes.get(k);
			if(n instanceof MessagePane)
				nodes.remove(k);
		}

		add(mp, 0, 1);
	}

	public UserPane getUserPane() {
		return userPane;
	}

}