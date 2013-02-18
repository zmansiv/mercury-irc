package com.mercuryirc.client.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class RightButtonBox extends HBox {
	public RightButtonBox() {
		setId("right-button-box");
		getChildren().add(new Label("RightButtonBox"));
	}
}
