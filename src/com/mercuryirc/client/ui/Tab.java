package com.mercuryirc.client.ui;

import com.mercuryirc.client.protocol.model.Target;

public class Tab {
	private Target target;
	private MessagePane messagePane;

	public Tab(ApplicationPane appPane, Target target) {
		this.target = target;
		this.messagePane = new MessagePane(appPane);
	}

	public Target getTarget() {
		return target;
	}

	public MessagePane getMessagePane() {
		return messagePane;
	}
}
