package com.mercuryirc.network.numerics;

import com.mercuryirc.network.Connection;

public class Unknown implements Connection.NumericHandler {

	@Override
	public boolean applies(Connection connection, int numeric) {
		return numeric == 421;
	}

	@Override
	public void process(Connection connection, String line, String[] parts) {
		connection.getCallback().onUnknownCommand(connection, parts[3]);
	}

}