package com.mercuryirc.network.callback;

import com.mercuryirc.model.Channel;
import com.mercuryirc.model.Entity;
import com.mercuryirc.model.Message;
import com.mercuryirc.model.Mode;
import com.mercuryirc.model.User;
import com.mercuryirc.network.Connection;

import java.util.List;
import java.util.Set;

/**
 * Contains empty implementations of Callback methods
 * so that clients can pick and choose which methods they would like
 * to implement without adhering to the whole Callback interface.
 */
public class CallbackAdapter implements Callback {

	public void onConnect(Connection connection) { }

	public void onPrivmsg(Connection connection, Message message) { }

	public void onNotice(Connection connection, Message message) { }

	public void onCtcp(Connection connection, Message message) { }

	public void onJoin(Connection connection, Channel channel, User user) { }

	public void onPart(Connection connection, Channel channel, User user, String reason) { }

	public void onQuit(Connection connection, User user, String reason) { }

	public void onNickList(Connection connection, Channel channel, List<User> users) {}

	public void onTopic(Connection connection, Channel channel, User source, String topic) { }

	public void onNick(Connection connection, User user, String oldNick) { }

	public void onKick(Connection connection, Channel channel, User user, String reason) { }

	public void onModeList(Connection connection, Channel channel, Mode.Type type, List<Mode> list) { }

	public void onError(Connection connection, String error) { }

	public void onUnknownCommand(Connection connection, String command) { }

	public void onMode(Connection connection, Entity target, Set<Mode> modes, boolean add) { }

    public void onJoinError(Connection connection, Channel channel, String reason) { }

    public void onPrivmsgOut(Connection connection, Message message) { }

	public void onNoticeOut(Connection connection, Message message) { }

	public void onCtcpOut(Connection connection, Message message) { }

	public void onQueryOut(Connection connection, User user) { }

	public void onJoinOut(Connection connection, Channel channel) { }

	public void onPartOut(Connection connection, Channel channel) { }

	public void onConnectionRequestOut(Connection connection, String network, String hostname, int port, String nick) { }

	public void onQuitOut(Connection connection, User user, String reason) { }

}