package com.mercuryirc.client.protocol;

public class IRCTools {
	/**
	 *
	 * @param in a string like ":irc.whatever.com" or ":Nick!User@Host"
	 * @return the properly parsed target, e.g. "irc.whatever.com" or "Nick"
	 */
	public static String parseTarget(String in) {
		if(in.contains("!"))
			return in.substring(1, in.indexOf('!'));
		else
			return in.substring(1);
	}
	/**
	 * @return a timestamp in IRC format (seconds, not milliseconds
	 * since the Unix epoch)
	 */
	public static long getTimestamp() {
		return System.currentTimeMillis() / 1000;
	}
}
