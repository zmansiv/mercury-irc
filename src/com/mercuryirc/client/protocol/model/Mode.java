package com.mercuryirc.client.protocol.model;

/** entry in the ban, invite, except lists */
public class Mode {
	public static enum Type { BAN, INVITE, EXCEPT }

	public String hostmask;
	public String setBy;
	public long timestamp;

	public Mode() {

	}

	public Mode(String hostmask, String setBy, long timestamp) {
		this.hostmask = hostmask;
		this.setBy = setBy;
		this.timestamp = timestamp;
	}
}
