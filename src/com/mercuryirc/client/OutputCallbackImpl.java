package com.mercuryirc.client;

import com.mercuryirc.client.protocol.model.Message;
import com.mercuryirc.client.protocol.network.Connection;
import com.mercuryirc.client.protocol.network.callback.OutputCallback;
import com.mercuryirc.client.ui.ApplicationPane;
import com.mercuryirc.client.ui.model.MessageRow;

public class OutputCallbackImpl implements OutputCallback {

	private final ApplicationPane appPane;

	public OutputCallbackImpl(ApplicationPane appPane) {
		this.appPane = appPane;
	}

	@Override
	public void onMessage(Connection connection, Message message) {
		appPane.getTabPane().get(appPane.getConnection().resolveTarget(message.getTarget())).getContentPane().getMessagePane().addRow(new MessageRow(appPane.getConnection(), message));
	}

}