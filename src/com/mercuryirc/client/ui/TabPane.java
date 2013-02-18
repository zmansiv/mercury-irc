package com.mercuryirc.client.ui;

import javafx.scene.layout.BorderPane;

public class TabPane extends BorderPane {
	private ApplicationPane appPane;

	private LeftButtonBox buttons;
	private TabBox tabs;

	public TabPane(ApplicationPane ap) {
		appPane = ap;

		buttons = new LeftButtonBox();
		tabs = new TabBox();

		setTop(buttons);
		setCenter(tabs);
	}
}
