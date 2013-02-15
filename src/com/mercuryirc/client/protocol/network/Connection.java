package com.mercuryirc.client.protocol.network;

import com.mercuryirc.client.protocol.network.callback.IrcCallback;
import com.mercuryirc.client.protocol.model.*;

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 * The IRC model is based entirely on responses from the server to prevent
 * client-server inconsistencies. For example, the joinChannel() method only
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

	private final IrcCallback callback;
	private ExceptionHandler exceptionHandler;

	private boolean registered;

	public Connection(Server server, User user, IrcCallback callback) {
		this.server = server;
		this.localUser = user;
		this.callback = callback;
	}

	public Server getServer() {
		return server;
	}

	public User getLocalUser() {
		return localUser;
	}

	public IrcCallback getCallback() {
		return callback;
	}

	public boolean isLocalUser(String nick) {
		return nick.equalsIgnoreCase(localUser.getName());
	}

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
		for (String line = readLine(); line != null; line = readLine()) {
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

	public void joinChannel(String channel) {
		writeLine("JOIN " + channel);
	}

	public void disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			if (exceptionHandler != null)
				exceptionHandler.onException(this, e);
		}
	}

	public void writeLine(String rawLine) {
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
