package com.mercuryirc.client.ui.model;

import com.mercuryirc.client.protocol.model.Target;
import com.mercuryirc.client.protocol.network.Connection;
import com.mercuryirc.client.ui.MessagePanel;
import com.mercuryirc.client.ui.UserPanel;

public class TargetTreeLabel extends TreeLabel {

	private final Target target;
	private final UserPanel userPanel;
	private final MessagePanel messagePanel;

	public static final TargetTreeLabel NETWORKS = new TargetTreeLabel(Type.NETWORKS);

	private TargetTreeLabel(String name, Connection connection, Target target, Type type) {
		super(name, connection, target, type);
		this.target = target;
		userPanel = new UserPanel(connection, target);
		messagePanel = new MessagePanel();
	}

	private TargetTreeLabel(Type type) {
		this(type.toString(), null, null, type);
	}

	public TargetTreeLabel(Connection connection, com.mercuryirc.client.protocol.model.Target target) {
		this(target.getName(), connection, target, null);
	}

	public Target getTarget() {
		return target;
	}

	public UserPanel getUserPanel() {
		return userPanel;
	}

	public MessagePanel getMessagePanel() {
		return messagePanel;
	}

}