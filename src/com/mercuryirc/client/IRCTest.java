package com.mercuryirc.client;

import java.io.IOException;

import com.mercuryirc.client.protocol.network.*;
import com.mercuryirc.client.protocol.model.*;

public class IRCTest implements Connection.ExceptionHandler {
	public static void main(String[] args) throws IOException {
		User me = new User("Tekk|Mercury");
		me.setUser("user");
		me.setRealName("MercuryIRC");

		Server svr = new Server("irc.rizon.net", 6697, true);
		Connection conn = new Connection(svr);

		conn.setAcceptAllSSLCerts(true);
		conn.setExceptionHandler(new IRCTest());

		conn.connect();
		conn.registerAs(me);
		conn.joinChannel("#mercury");
	}

	public void onException(Exception e) {
		e.printStackTrace();
	}
}
