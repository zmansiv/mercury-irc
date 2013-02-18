package com.mercuryirc.client.protocol.model;

public class Message {

	private final Type type;

	/**
	 * 'source' could be a User object but I'd prefer that only the Server
	 * know about the Users
	 */
	private final String source;
	private final String target;

	private String message;

	/**
	 * timestamp in milliseconds
	 */
	private final long timestamp;

	public Message(Type type, String source, String target, String message) {
		this.type = type;
		this.source = source;
		this.target = target;
		this.message = message;
		this.timestamp = System.currentTimeMillis();
	}

	/**
	 * for outgoing messages, we don't care about the source
	 */
	public Message(Type type, String target, String message) {
		this(type, null, target, message);
	}

	public Type getType() {
		return type;
	}

	public String getSource() {
		return source;
	}

	public String getTarget() {
		return target;
	}

	public String getMessage() {
		return message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String toString() {
		return type + "(ts: " + timestamp + ", " + "source: " + source + ", target: " + target + ", message: " + message + ")";
	}

	public static enum Type {PRIVMSG, NOTICE}

}
