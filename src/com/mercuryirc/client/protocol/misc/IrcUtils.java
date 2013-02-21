package com.mercuryirc.client.protocol.misc;

public class IrcUtils {

	/**
	 * @param in a string like ":irc.whatever.com" or ":Nick!User@Host"
	 * @return the properly parsed source, e.g. "irc.whatever.com" or "Nick"
	 */
	public static String parseSource(String in) {
		if (in.contains("!"))
			return in.substring(1, in.indexOf('!'));
		else
			return in.substring(1);
	}

	/**
	 * @return a timestamp in IRC format (seconds, not milliseconds
	 *         since the Unix epoch)
	 */
	public static long getTimestamp() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * @return true if the specified character is an IRC rank character
	 * ('+', '%', '@', '&', or '~')
	 */
	public static boolean isRank(char ch) {
		return ch == '+' || ch == '%' || ch == '@' || ch == '&' || ch == '~';
	}
}
