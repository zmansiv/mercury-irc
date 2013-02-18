package com.mercuryirc.client.ui.model;

import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Server;
import com.mercuryirc.client.ui.ChatPane;

public class Target {

	private final String name;
	private final com.mercuryirc.client.protocol.model.Target target;
	private final Type type;
	private final ChatPane chatPane;

	private Target(String name, com.mercuryirc.client.protocol.model.Target target, Type type) {
		this.name = name;
		if (type != null) {
			this.type = type;
		} else {
			if (target instanceof Server) {
				this.type = Type.NETWORK;
			} else if (target instanceof Channel) {
				this.type = Type.CHANNEL;
			} else {
				this.type = Type.USER;
			}
		}
		this.target = target;
		chatPane = new ChatPane();
	}

	public Target(com.mercuryirc.client.protocol.model.Target target) {
		this(target.getName(), target, null);
	}

	public String getName() {
		return name;
	}

	public com.mercuryirc.client.protocol.model.Target getTarget() {
		return target;
	}

	public ChatPane getChatPane() {
		return chatPane;
	}

	public Type getType() {
		return type;
	}

	public enum Type {

		NETWORK("network-label"), CHANNEL("channel-label"), USER("user-label");

		private final String style;

		Type(String style) {
			this.style = style;
		}

		public String getStyle() {
			return style;
		}

	}

}