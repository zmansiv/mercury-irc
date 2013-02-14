package com.mercuryirc.client.ui.model;

import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Server;
import com.mercuryirc.client.protocol.model.Target;
import com.mercuryirc.client.protocol.network.Connection;
import javafx.scene.control.Label;

public class TreeLabel extends Label {

	private final Connection connection;
	private final Type type;

	public static final TreeLabel USERS = new TreeLabel(Type.USERS);

	public TreeLabel(String name, Connection connection, Target target, Type type) {
		super(name);
		getStyleClass().add("target-cell");
		this.connection = connection;
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
		setId(this.type.getStyle());
	}

	private TreeLabel(Type type) {
		this(type.toString(), null, null, type);
	}

	public Connection getConnection() {
		return connection;
	}

	public Type getType() {
		return type;
	}

	public enum Type {
		NETWORKS("networks-cell"), NETWORK("network-cell"), CHANNEL("channel-cell"), USER("user-cell"), USERS("users-cell");

		private final String style;

		Type(String style) {
			this.style = style;
		}

		public String getStyle() {
			return style;
		}

	}

}