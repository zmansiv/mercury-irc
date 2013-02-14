package com.mercuryirc.client.ui.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {

	private final com.mercuryirc.client.protocol.model.Message message;
	private final String source;
	private final String content;
	private final Type type;

	public Message(com.mercuryirc.client.protocol.model.Message message) {
		this.message = message;
		String source, content;
		source = message.getSource();
		content = escape(findUrls(stripColors(message.getMessage())));
		switch (message.getType()) {
			default:
				this.source = source;
				this.content = content;
		}
		type = Type.OTHER;
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

	public com.mercuryirc.client.protocol.model.Message getMessage() {
		return message;
	}

	public String getSource() {
		return source;
	}

	public String getContent() {
		return content;
	}

	public Type getType() {
		return type;
	}

	public enum Type {

		ME("me"), OTHER("other"), HIGHLIGHT("highlight"), EVENT("event");

		private final String style;

		private Type(final String style) {
			this.style = style;
		}

		public String style() {
			return style;
		}

	}

}