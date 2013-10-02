package com.mercuryirc.network.callback;

import com.mercuryirc.model.Channel;
import com.mercuryirc.model.Entity;
import com.mercuryirc.model.Message;
import com.mercuryirc.model.Mode;
import com.mercuryirc.model.User;
import com.mercuryirc.network.Connection;

import java.util.List;
import java.util.Set;

public interface Callback {

	/** Called upon successful connection to the IRC network. */
	void onConnect(Connection connection);

	/** Called whenever someone sends a message to you or a channel you are in. */
	void onPrivmsg(Connection connection, Message message);

	void onNotice(Connection connection, Message message);

	void onCtcp(Connection connection, Message message);

	/** Called whenever any user joins a channel you are in. */
	void onJoin(Connection connection, Channel channel, User user);

	/** Called whenever any user parts a channel you are in. */
	void onPart(Connection connection, Channel channel, User user, String reason);

	/** Called whenever any user (who shares a common channel with you) quits. */
	void onQuit(Connection connection, User user, String reason);

	/** Called whenever all nicknames in the list have been received. */
	void onNickList(Connection connection, Channel channel, List<User> users);

	/** Called whenever the topic changes in a channel you are in. */
	void onTopic(Connection connection, Channel channel, User source, String topic);

	/** Called whenever any user (who shares a common channel with you) changes nicks. */
	void onNick(Connection connection, User user, String oldNick);

	void onKick(Connection connection, Channel channel, User user, String reason);

	void onModeList(Connection connection, Channel channel, Mode.Type type, List<Mode> list);

	void onError(Connection connection, String error);

	void onUnknownCommand(Connection connection, String command);

	void onMode(Connection connection, Entity target, Set<Mode> modes, boolean add);

    /** Called when joining a channel fails. */
    void onJoinError(Connection connection, Channel channel, String reason);

	//out

	void onPrivmsgOut(Connection connection, Message message);

	void onNoticeOut(Connection connection, Message message);

	void onCtcpOut(Connection connection, Message message);

	void onQueryOut(Connection connection, User user);

	void onJoinOut(Connection connection, Channel channel);

	void onPartOut(Connection connection, Channel channel);

	void onConnectionRequestOut(Connection connection, String network, String hostname, int port, String nick);

	void onQuitOut(Connection connection, User user, String reason);

}