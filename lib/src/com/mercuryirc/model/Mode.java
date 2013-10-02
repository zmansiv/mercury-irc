package com.mercuryirc.model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * entry in the ban, invite, except lists
 */
public class Mode {

	public static enum Type {

		UNKNOWN_MODE('?', Context.USER),
		HIDDEN('i', Context.USER), CLOAKED('x', Context.USER),
		BAN('b', Context.CHANNEL_USER), INVITE('i', Context.CHANNEL_USER), EXCEPT('e', Context.CHANNEL_USER),
		VOICE('v', Context.CHANNEL_USER), HALFOP('h', Context.CHANNEL_USER), OP('o', Context.CHANNEL_USER), PROTECT('a', Context.CHANNEL_USER), OWNER('q', Context.CHANNEL_USER),
		MODERATED('m', Context.CHANNEL), SECRET('s', Context.CHANNEL);

		private final char ch;
		private final Context context;

		private static final Queue<Character> unknownCharacters = new LinkedList<>();

		Type(char ch, Context context) {
			this.ch = ch;
			this.context = context;
		}

		public char getChar() {
			return ch;
		}

		private Context getContext() {
			return context;
		}

		public static Type fromChar(char ch, Context context) {
			for (Type type : values()) {
				if (type.getChar() == ch && type.getContext() == context) {
					return type;
				}
			}
			unknownCharacters.offer(ch);
			return UNKNOWN_MODE;
		}

		@Override
		public String toString() {
			return super.toString().replace('_', ' ') + " [" + (this == UNKNOWN_MODE ? unknownCharacters.poll() : getChar()) + "]";
		}

		public static enum Context {
			USER, CHANNEL, CHANNEL_USER
		}

	}

	private User source;
	private Entity target;
	private long timestamp;
	private Type type;

	public Mode(User source, Entity target, long timestamp, Type type) {
		this.source = source;
		this.target = target;
		this.timestamp = timestamp;
		this.type = type;
	}

	public User getSource() {
		return source;
	}

	public Entity getTarget() {
		return target;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public Type getType() {
		return type;
	}

}