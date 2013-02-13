package com.mercuryirc.client;

import java.io.IOException;

import com.mercuryirc.client.protocol.IRCCallback;
import com.mercuryirc.client.protocol.network.*;
import com.mercuryirc.client.protocol.model.*;

public class IRCTest implements Connection.ExceptionHandler, IRCCallback {
	public static void main(String[] args) throws IOException {
		IRCTest test = new IRCTest();

		User me = new User("Tekk|Mercury");
		me.setUser("user");
		me.setRealName("MercuryIRC");

		Server svr = new Server("irc.rizon.net", 6697, true);
		Connection conn = new Connection(svr, test);

		conn.setAcceptAllSSLCerts(true);
		conn.setExceptionHandler(test);

		conn.connect();
		conn.registerAs(me);
		conn.joinChannel("#mercury");
	}

	@Override
	public void onException(Exception e) {
		e.printStackTrace();
	}

	@Override
	public void onMessage(Connection conn, Message msg) {
		System.out.println(msg.getFrom() + ": " + msg.getMessage());
	}

	@Override
	public void onChannelJoin(Connection conn, User user, Channel chan) {
		System.out.println(user.getNick() + " has joined " + chan.getName());
	}
}
