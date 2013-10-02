package com.mercuryirc.misc;

import com.mercuryirc.model.Mode;

public class IrcUtils {

	/**
	 * @param in a string like ":chat.whatever.com" or ":Nick!User@Host"
	 * @return the properly parsed source, e.g. "chat.whatever.com" or "Nick"
	 */
	public static String parseSource(String in) {
		if (in.startsWith(":")) {
			in = in.substring(1);
		}
		if (in.contains("!")) {
			in = in.substring(0, in.indexOf('!'));
		}
		return in;
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
	 *         ('+', '%', '@', '&', or '~')
	 */
	public static boolean isRank(char ch) {
		return ch == '+' || ch == '%' || ch == '@' || ch == '&' || ch == '~';
	}

	public static Mode.Type rankFromChar(char ch) {
		switch (ch) {
			case '+':
				return Mode.Type.VOICE;
			case '%':
				return Mode.Type.HALFOP;
			case '@':
				return Mode.Type.OP;
			case '&':
				return Mode.Type.PROTECT;
			case '~':
				return Mode.Type.OWNER;
			default:
				return null;
		}
	}

}