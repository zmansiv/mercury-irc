package com.mercuryirc.client;

import java.io.IOException;

import com.mercuryirc.client.protocol.IRCCallback;
import com.mercuryirc.client.protocol.network.*;
import com.mercuryirc.client.protocol.model.*;

public class IRCTest implements Connection.ExceptionHandler, IRCCallback {
	public static void main(String[] args) throws IOException {
		Server svr = new Server("irc.rizon.net", 6697, true);

		User me = new User("Tekk|Mercury");
		me.setUser("user");
		me.setRealName("MercuryIRC");

		IRCTest test = new IRCTest();

		Connection conn = new Connection(svr, me, test);

		conn.setAcceptAllSSLCerts(true);
		conn.setExceptionHandler(test);

		conn.connect();
		conn.joinChannel("#mercury");
	}

	@Override
	public void onException(Exception e) {
		e.printStackTrace();
	}

	@Override
	public void onMessage(Message msg) {
		System.out.println(msg);
	}

	@Override
	public void onChannelJoin(User user, Channel chan) {
		System.out.println(user.getNick() + " has joined " + chan.getName());
	}
}
