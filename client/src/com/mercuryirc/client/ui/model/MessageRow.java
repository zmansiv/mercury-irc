package com.mercuryirc.client.ui.model;

import com.mercuryirc.model.Message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageRow {

	private final String source;
	private final String message;
	private final long time;
	private final Type type;

	public MessageRow(Message message, Type type) {
		this.type = type;
		this.time = message.getTimestamp();
		String _message = escapeLine(stripColors(message.getMessage()));
		switch (type) {
			case PRIVMSG:
			case HIGHLIGHT:
			case SELF:
				this.source = message.getSource().getName();
				this.message = _message;
				break;
			case NOTICE:
				this.source = message.getSource().getName();
				this.message = "[NOTICE" + (message.getTarget() == null ? "" : " to " + message.getTarget().getName()) + "] " + _message;
				break;
			case CTCP:
				this.source = message.getSource().getName();
				this.message = "[CTCP" + (message.getTarget() == null ? "" : " to " + message.getTarget().getName()) + "] " + _message;
				break;
			default:
				this.source = "";
				this.message = (message.getSource() == null ? "" : (message.getSource().getName() + " ")) + _message;
		}
	}

	private static String stripColors(String line) {
		return line.replaceAll("[\u0003]+[\\d](\\d)?(,[\\d](\\d)?)?", "");
	}

	private static String escapeLine(String line) {
		StringBuilder result = new StringBuilder();
		Matcher m = Pattern.compile("(?i)\\b((?:[a-z][\\w-]+:(?:/{1,3}|[a-z0-9%])|www\\d{0,3}[.]|[a-z0-9.\\-]+[.][a-z]{2,4}/)(?:[^\\s()<>]+|\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\))+(?:\\(([^\\s()<>]+|(\\([^\\s()<>]+\\)))*\\)|[^\\s`!()\\[\\]{};:'\".,<>?«»“”‘’]))").matcher(line);
		int lastMatchEnd = 0;
		while (m.find()) {
			String url = m.group();
			if (m.start() != 0) {
				result.append(escape(line.substring(lastMatchEnd, m.start())));
			}
			lastMatchEnd = m.end();
			String escaped = escape(url);
			result.append(String.format("<a href=javascript:onLinkClick(\\'%s\\')>%s</a>", escaped, escaped));
		}
		if (lastMatchEnd != line.length()) {
			result.append(escape(line.substring(lastMatchEnd)));
		}
		return result.toString();
	}

	private static String escape(String line) {
		return line.replace("\\", "\\\\").replace("&", "&amp;").replace("'", "&apos;").replace("\"", "&quot;").replace("<", "&#60;").replace(">", "&gt;");
	}

	public String getSource() {
		return source;
	}

	public String getMessage() {
		return message;
	}

	public long getTime() {
		return time;
	}

	public Type getType() {
		return type;
	}

	public enum Type {

		SELF("self"),
		PRIVMSG("privmsg"),
		HIGHLIGHT("highlight"),
		NOTICE("notice"),
		CTCP("notice"),
		EVENT("event"),
		JOIN("join"),
		PART("part"),
		ERROR("error");

		private final String style;

		private Type(String style) {
			this.style = style;
		}

		public String style() {
			return style;
		}

	}

}