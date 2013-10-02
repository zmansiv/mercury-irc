package com.mercuryirc.network;

import com.mercuryirc.network.commands.Error;
import com.mercuryirc.network.commands.Join;
import com.mercuryirc.network.commands.Kick;
import com.mercuryirc.network.commands.Mode;
import com.mercuryirc.network.commands.Nick;
import com.mercuryirc.network.commands.Notice;
import com.mercuryirc.network.commands.Part;
import com.mercuryirc.network.commands.Ping;
import com.mercuryirc.network.commands.Privmsg;
import com.mercuryirc.network.commands.Quit;
import com.mercuryirc.network.commands.Topic;

import java.util.ArrayList;
import java.util.List;

public class CommandHandlers {

	public static final List<Connection.CommandHandler> list = new ArrayList<Connection.CommandHandler>(3);

	static {
		list.add(new Ping());
		list.add(new Join());
		list.add(new Privmsg());
		list.add(new Notice());
		list.add(new Part());
		list.add(new Quit());
		list.add(new Topic());
		list.add(new Nick());
		list.add(new Kick());
		list.add(new Mode());
		list.add(new Error());
	}

}
