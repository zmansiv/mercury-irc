package com.mercuryirc.network.commands;

import com.mercuryirc.misc.IrcUtils;
import com.mercuryirc.model.Channel;
import com.mercuryirc.model.Entity;
import com.mercuryirc.model.Server;
import com.mercuryirc.model.User;
import com.mercuryirc.network.Connection;
import javafx.application.Platform;

import java.util.HashSet;
import java.util.Set;

public class Mode implements Connection.CommandHandler {

	@Override
	public boolean applies(Connection connection, String command, String line) {
		return command.equals("MODE");
	}

	@Override
	public void process(Connection connection, String line, String[] parts) {
		String _source = IrcUtils.parseSource(parts[0]), _target = parts[2], _mode = parts[3], __target2 = null;
		if (parts.length > 4) {
			__target2 = parts[4];
		}
		Server server = connection.getServer();
		User source = server.getUser(_source);
		final Entity target;
		if (_target.startsWith("#")) {
			target = server.getChannel(_target);
		} else {
			target = server.getUser(_target);
		}
		Entity _target2 = target;
		if (__target2 != null) {
			_target2 = server.getUser(__target2);
		}
		final Entity target2 = _target2;
		boolean add = _mode.charAt(0) == '+';
		Set<com.mercuryirc.model.Mode> modes = new HashSet<>();
		for (char ch : _mode.substring(1).toCharArray()) {
			modes.add(new com.mercuryirc.model.Mode(source, _target2, IrcUtils.getTimestamp(), com.mercuryirc.model.Mode.Type.fromChar(ch, target instanceof User ? com.mercuryirc.model.Mode.Type.Context.USER : (_target2 instanceof User ? com.mercuryirc.model.Mode.Type.Context.CHANNEL_USER : com.mercuryirc.model.Mode.Type.Context.CHANNEL))));
		}
		if (target instanceof Channel && _target2 instanceof User) {
			for (final com.mercuryirc.model.Mode mode : modes) {
				if (add) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							((User) target2).addChannelRank((Channel) target, mode.getType());
						}
					});
				} else {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							((User) target2).removeChannelRank((Channel) target, mode.getType());
						}
					});
				}
			}
		}
		connection.getCallback().onMode(connection, target, modes, add);
	}

}