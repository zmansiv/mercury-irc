package com.mercuryirc.client.protocol.model;

public class Message {
	public static enum Kind { PRIVMSG, NOTICE }

	private Kind kind;

	private String from;
	private String to;

	private String message;
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
}
