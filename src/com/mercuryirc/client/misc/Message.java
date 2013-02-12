package com.mercuryirc.client.misc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {

	private final String source;
	private final String message;
	private final MessageType type;

	public Message(String source, String message, MessageType type) {
		this.type = type;
		source = escape(source);
		message = escape(findUrls(stripColors(message)));
		switch (type) {
			case SERVER:
				this.source = "";
				this.message = source + " " + message;
				break;
			default:
				this.source = source;
				this.message = message;
		}
	}

	private static String stripColors(String line) {
		return line.replaceAll("[\u0003]+[\\d](\\d)?(,[\\d](\\d)?)?", "");
	}

	private static String findUrls(String line) {
		StringBuilder result = new StringBuilder();
		Matcher m = Pattern.compile("(?i)\\b((?:[a-z][\\w-]+:(?:/{1,3}|[a-z0-9%])|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))").matcher(line);
		int lastMatchEnd = 0;
		while (m.find()) {
			String url = m.group();
			if (m.start() != 0) {
				result.append(line.substring(lastMatchEnd, m.start()));
			}
			lastMatchEnd = m.end();
			result.append(String.format("<a href=javascript:onLinkClick('%s')>%s</a>", url, url));
		}
		if (lastMatchEnd != line.length()) {
			result.append(line.substring(lastMatchEnd));
		}
		return result.toString();
	}

	private static String escape(String line) {
		return line.replace("'", "\\'").replace("\"", "\\\"");
	}

	public String source() {
		return source;
	}

	public String message() {
		return message;
	}

	public MessageType type() {
		return type;
	}

	public enum MessageType {

		ME("me"), OTHER("other"), HIGHLIGHT("highlight"), SERVER("server");

		private final String style;

		private MessageType(final String style) {
			this.style = style;
		}

		public String style() {
			return style;
		}

	}

}