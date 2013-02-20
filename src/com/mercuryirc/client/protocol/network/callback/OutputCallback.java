package com.mercuryirc.client.protocol.network.callback;

import com.mercuryirc.client.protocol.model.Message;
import com.mercuryirc.client.protocol.network.Connection;

public interface OutputCallback {

	public void onMessage(Connection connection, Message message);

}