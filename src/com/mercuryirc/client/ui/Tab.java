package com.mercuryirc.client.ui;

import com.mercuryirc.client.protocol.model.Target;

public class Tab {

	private final Target target;

	private final ContentPane contentPane;

	public Tab(ApplicationPane appPane, Target target) {
		this.target = target;

		this.contentPane = new ContentPane(appPane);
	}

	public Target getTarget() {
		return target;
	}

	public ContentPane getContentPane() {
		return contentPane;
	}

}