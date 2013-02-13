package com.mercuryirc.client.protocol.model;

import com.mercuryirc.client.protocol.IRCTools;

public class Message {
	public static enum Kind { PRIVMSG, NOTICE }

	private Kind kind;

	/**
	 *'from' could be a User object but I'd prefer that only the Server
	 * know about the Users
	 */
	private String from;
	private String to;

	private String message;

	/** timestamp in milliseconds */
	private long timestamp;

	public Message(Kind kind, String from, String to, String message) {
		this.kind = kind;
		this.from = from;
		this.to = to;
		this.message = message;
		this.timestamp = System.currentTimeMillis();
	}

	public Kind getKind() {
		return kind;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public String getMessage() {
		return message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String toString() {
		return kind + "(ts: " + timestamp + ", " + "from: " + from + ", to: " + to + ", message: " + message + ")";
	}
}
