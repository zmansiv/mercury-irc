package com.mercuryirc.client.ui;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TabPane extends VBox {

	private final ApplicationPane appPane;

	public TabPane(ApplicationPane appPane) {
		this.appPane = appPane;
		setMinWidth(200);
		VBox tabListBox = new VBox();
		tabListBox.getStyleClass().add("dark-pane");
		tabListBox.setId("tab-list");
		setVgrow(tabListBox, Priority.ALWAYS);
		ListView tabList = new ListView();
		setVgrow(tabList, Priority.ALWAYS);
		tabList.getItems().add(new Label("TabList"));
		tabListBox.getChildren().add(tabList);
		getChildren().addAll(new TabButtonPane(), tabListBox);
	}

	private class TabButtonPane extends HBox {

		public TabButtonPane() {
			getStyleClass().add("dark-pane");
			setId("tab-button-pane");
			setMinHeight(85);
			getChildren().add(new Label("TabButtonPane"));
		}

	}

}