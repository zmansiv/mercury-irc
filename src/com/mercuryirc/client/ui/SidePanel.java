package com.mercuryirc.client.ui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public abstract class SidePanel extends VBox {

	public SidePanel() {
		setMinWidth(175);
		VBox contentBox = new VBox();
		contentBox.getStyleClass().add("side-panel");
		VBox.setVgrow(contentBox, Priority.ALWAYS);
		HBox bottomBar = new HBox();
		bottomBar.getStyleClass().add("bottom-bar");
		bottomBar.setMinHeight(28);
		bottomBar.setMaxHeight(28);
		initialize(contentBox);
		getChildren().addAll(contentBox, bottomBar);
	}

	protected abstract void initialize(VBox contentBox);

}
