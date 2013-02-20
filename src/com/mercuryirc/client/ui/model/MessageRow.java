package com.mercuryirc.client.ui.model;

import com.mercuryirc.client.protocol.model.Message;
import com.mercuryirc.client.protocol.network.Connection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageRow {

	private final String source;
	private final String target;
	private final String content;
	private final Type type;

	public MessageRow(Connection connection, Message message) {
		this.source = message.getSource();
		this.target = message.getTarget();
		this.content = escape(findUrls(stripColors(message.getMessage())));
		String localNick = connection.getLocalUser().getName();
		type = content.toLowerCase().contains(localNick.toLowerCase()) ? Type.HIGHLIGHT : source == null || source.equals("") ? Type.EVENT : Type.NORMAL;
	}

	public MessageRow(String target, String content, Type type) {
		this.source = "";
		this.target = target;
		this.content = content;
		this.type = type;
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

	public String getSource() {
		return source;
	}

	public String getTarget() {
		return target;
	}

	public String getContent() {
		return content;
	}

	public Type getType() {
		return type;
	}

	public enum Type {

		NORMAL("normal"), HIGHLIGHT("highlight"), EVENT("event");

		private final String style;

		private Type(final String style) {
			this.style = style;
		}

		public String style() {
			return style;
		}

	}

}