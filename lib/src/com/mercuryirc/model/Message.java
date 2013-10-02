package com.mercuryirc.model;

public class Message {

	private final Entity source;
	private final Entity target;

	private String message;

	/**
	 * timestamp in milliseconds
	 */
	private final long timestamp;

	public Message(Entity source, Entity target, String message) {
		this.source = source;
		this.target = target;
		this.message = message;
		this.timestamp = System.currentTimeMillis();
	}

	public Entity getSource() {
		return source;
	}

	public Entity getTarget() {
		return target;
	}

	public String getMessage() {
		return message;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String toString() {
		return "(ts: " + timestamp + ", " + "source: " + source + ", target: " + target + ", message: " + message + ")";
	}

}