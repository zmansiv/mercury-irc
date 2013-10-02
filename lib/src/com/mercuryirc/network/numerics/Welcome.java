package com.mercuryirc.network.numerics;

import com.mercuryirc.network.Connection;

public class Welcome implements Connection.NumericHandler {
	public boolean applies(Connection connection, int numeric) {
		return numeric == 1;
	}

	@Override
	public void process(Connection connection, String line, String[] parts) {
		connection.setRegistered(true);
		connection.getCallback().onConnect(connection);
	}
}
