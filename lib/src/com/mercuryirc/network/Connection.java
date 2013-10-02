package com.mercuryirc.network;

import com.mercuryirc.misc.IrcUtils;
import com.mercuryirc.model.Channel;
import com.mercuryirc.model.Entity;
import com.mercuryirc.model.Message;
import com.mercuryirc.model.Server;
import com.mercuryirc.model.User;
import com.mercuryirc.network.callback.Callback;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * The IRC model is based entirely on responses from the server to prevent
 * client-server inconsistencies. For example, the join() method only
 * sends the request to the server to join the channel; it does not add
 * a new Channel object to the Server until the response is received from
 * the server saying that the channel was successfully joined.
 */
public class Connection implements Runnable {

	private final Server server;

	private boolean acceptAllCerts;

	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;

	private final User localUser;

	private final Callback callback;
	private ExceptionHandler exceptionHandler;

	private boolean registered;

	public Connection(Server server, User localUser, Callback callback) {
		this.server = server;
		this.localUser = localUser;
		server.addLocalUser(localUser);
		this.callback = callback;
	}

	/* accessors */

	public Server getServer() {
		return server;
	}

	public User getLocalUser() {
		return localUser;
	}

	public Callback getCallback() {
		return callback;
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean r) {
		registered = r;
	}

	/**
	 * security risk
	 */
	public void setAcceptAllSSLCerts(boolean accept) {
		acceptAllCerts = accept;
	}

	/* raw network methods */

	public void connect() {
		try {
			if (server.isSsl()) {
				SSLSocketFactory ssf;

				if (acceptAllCerts)
					ssf = getLenientSocketFactory();
				else
					ssf = (SSLSocketFactory) SSLSocketFactory.getDefault();

				socket = ssf.createSocket(server.getHost(), server.getPort());
			} else {
				socket = new Socket(server.getHost(), server.getPort());
			}

			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			writeLine("NICK " + localUser.getName());
			writeLine("USER " + localUser.getUserName() + " * * :" + localUser.getRealName());

			new Thread(this).start();
		} catch (IOException e) {
			if (exceptionHandler != null)
				exceptionHandler.onException(this, e);
		}
	}

	/**
	 * Users of this class should not call this method directly,
	 * use connect() instead.
	 */
	public void run() {
		for (String line = readLine(); ; line = readLine()) {
			if (line == null) {
				callback.onQuitOut(this, getLocalUser(), "Connection closed");
				break;
			}

			String[] parts = line.split(" ");

			String command;
			if (line.startsWith(":"))
				command = parts[1];
			else
				command = parts[0];

			try {
				int numeric = Integer.parseInt(command);

				for (NumericHandler nh : NumericHandlers.list)
					if (nh.applies(this, numeric))
						nh.process(this, line, parts);
			} catch (NumberFormatException e) {
				// not a numeric
				for (CommandHandler lh : CommandHandlers.list)
					if (lh.applies(this, command, line))
						lh.process(this, line, parts);
			}
		}
	}

	public void disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			if (exceptionHandler != null)
				exceptionHandler.onException(this, e);
		}
	}

	private void writeLine(String rawLine) {
		try {
			System.out.println("(out) " + rawLine);
			out.write(rawLine + "\r\n");
			out.flush();
		} catch (IOException e) {
			if (exceptionHandler != null)
				exceptionHandler.onException(this, e);
		}
	}

	public String readLine() {
		try {
			String s = in.readLine();
			System.out.println(" (in) " + s);
			return s;
		} catch (IOException e) {
			if (exceptionHandler != null)
				exceptionHandler.onException(this, e);
			return null;
		}
	}

	/* IRC commands */

	public void privmsg(Message message) {
		privmsg(message, false);
	}

	public void privmsg(Message message, boolean silent) {
		writeLine("PRIVMSG " + message.getTarget().getName() + " :" + message.getMessage());
		if (!silent) {
			callback.onPrivmsgOut(this, message);
		}
	}

	public void notice(Message message) {
		writeLine("NOTICE " + message.getTarget().getName() + " :" + message.getMessage());
		callback.onNoticeOut(this, message);
	}

	public void join(String channel) {
		writeLine("JOIN " + channel);
		callback.onJoinOut(this, server.getChannel(channel));
	}

	public void part(Channel channel) {
		writeLine("PART " + channel.getName());
		for (User user : channel.getUsers()) {
			user.removeChannel(channel);
		}
		channel.getUsers().clear();
		callback.onPartOut(this, channel);
	}

	public void kick(Channel channel, User user) {
		writeLine("KICK " + channel.getName() + " " + user.getName());
	}

	public void whois(String nick) {
		writeLine("WHOIS " + nick);
	}

	public void who(String channel) {
		writeLine("WHO " + channel);
	}

	public void nick(String nick) {
		writeLine("NICK " + nick);
	}

	public void pong(String pong) {
		writeLine("PONG :" + pong);
	}

	public void quit(String reason) {
		writeLine("QUIT :" + reason);
	}

	public void ctcp(Entity target, String message) {
		writeLine("PRIVMSG " + target.getName() + " :\u0001" + message + "\u0001");
		Message _message = new Message(localUser, target, message);
		callback.onCtcpOut(this, _message);
	}

	public void setChannelModes(String channel, String modes, String... args) {
		String send = "MODE " + channel + " " + modes + " ";
		for (String a : args)
			send += a + " ";
		send = send.substring(0, send.lastIndexOf(' '));
		writeLine(send);
	}

	public void ban(String channel, String user) {
		setHostMode(channel, user, "+b");
	}

	public void unban(String channel, String user) {
		setHostMode(channel, user, "-b");
	}

	public void invite(String channel, String user) {
		setHostMode(channel, user, "+I");
	}

	public void uninvite(String channel, String user) {
		setHostMode(channel, user, "-I");
	}

	public void except(String channel, String user) {
		setHostMode(channel, user, "+e");
	}

	public void unexcept(String channel, String user) {
		setHostMode(channel, user, "-e");
	}

	private void setHostMode(String channel, String mask, String mode) {
		if (mask.contains("!") && mask.contains("@")) {
			writeLine("MODE " + channel + " " + mode + " " + mask);
		} else {
			// attempt to find the user's host
			User u = server.getUser(mask, false);
			if (u.getHost() == null)
				return;

			writeLine("MODE " + channel + " " + mode + " *!*@" + u.getHost());
		}
	}

	public void process(Entity target, String input) {
		if (input.startsWith("/")) {
			input = input.substring(1);
			String input2 = input.toLowerCase().trim();
			final String[] tokens = input2.split(" ");
			if (input2.startsWith("query") && tokens.length > 1) {
				String nick = tokens[1];
				if (IrcUtils.isRank(nick.charAt(0))) {
					nick = nick.substring(1);
				}
				callback.onQueryOut(this, server.getUser(nick, true));
			} else if (input2.startsWith("msg")) {
				if (tokens.length > 2 && !tokens[1].startsWith("#")) {
					if (tokens[1].equals("nickserv") && (tokens[2].equals("identify") || tokens[2].equals("id"))) {
						localUser.setNickservPassword(tokens[3]);
					}
					Message message = new Message(localUser, tokens[1].startsWith("#") ? server.getChannel(tokens[1]) : server.getUser(tokens[1]), input.substring(input2.indexOf(tokens[2])));
					privmsg(message);
					callback.onPrivmsgOut(this, message);
				}
			} else if ((input2.equals("part") || input2.equals("p")) && target instanceof Channel) {
				part((Channel) target);
			} else if (tokens[0].matches("j|join") && tokens[1].startsWith("#")) {
				join(input.split(" ")[1]);
			} else if (tokens[0].equals("topic") && !tokens[1].startsWith("#") && target instanceof Channel) {
				writeLine("TOPIC " + target.getName() + " " + input.substring(input2.indexOf(tokens[1])));
			} else if (tokens[0].equals("nick") && tokens.length == 2) {
				nick(input.split(" ")[1]);
			} else if (tokens[0].equals("server")) {
				if (tokens.length == 3) {
					String hostname = tokens[1];
					int port = 6667;
					if (tokens[1].contains(":")) {
						int sep = tokens[1].indexOf(':');
						hostname = tokens[1].substring(0, sep);
						port = Integer.parseInt(tokens[1].substring(sep + 1));
					}
					int start = input2.indexOf(" " + tokens[2]) + 1;
					callback.onConnectionRequestOut(this, input.substring(start, start + tokens[2].length()), hostname, port, getLocalUser().getName());
				} else {
					callback.onError(this, "Usage: /server hostname:port network_name");
					callback.onError(this, "e.g. /server " + server.getHost() + ":" + server.getPort() + " " + server.getName());
				}
			} else if (tokens[0].equals("ctcp")) {
				String message = tokens[2].toUpperCase();
				int extended = 7 + tokens[1].length() + message.length();
				if (input.length() > extended) {
					message += input.substring(extended);
				}
				ctcp(server.getUser(tokens[1]), message);
			} else if (tokens[0].equals("me")) {
				ctcp(target, "ACTION " + input.substring(3));
			} else if (tokens[0].equals("notice")) {
				notice(new Message(getLocalUser(), server.getUser(tokens[1]), input.substring(8 + tokens[1].length())));
			} else {
				if (input2.startsWith("ns identify ") || input2.startsWith("ns id ")) {
					localUser.setNickservPassword(tokens[2]);
				}
				writeLine(input);
			}
		} else {
			if (target.getName().equalsIgnoreCase("nickserv") && input.toLowerCase().startsWith("identify")) {
				localUser.setNickservPassword(input.substring(input.indexOf(' ') + 1));
			}
			Message message = new Message(localUser, target, input);
			privmsg(message);
		}

	}

	/* internal utility method used to accept all SSL certificates */

	private SSLSocketFactory getLenientSocketFactory() {
		TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		}};

		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new SecureRandom());
			return sc.getSocketFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/* external interfaces */

	public static interface ExceptionHandler {

		public void onException(Connection connection, Exception e);

	}

	public static interface CommandHandler {

		public boolean applies(Connection connection, String command, String line);

		public void process(Connection connection, String line, String[] parts);

	}

	public static interface NumericHandler {

		public boolean applies(Connection connection, int numeric);

		public void process(Connection connection, String line, String[] parts);

	}

}
