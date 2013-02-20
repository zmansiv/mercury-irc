package com.mercuryirc.client.ui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;


public class ContentPane extends GridPane {

	private ApplicationPane appPane;

	public ContentPane(ApplicationPane appPane) {
		this.appPane = appPane;

		add(new TopicPane(appPane), 0, 0, 2, 1);
		add(new MessagePane(appPane), 0, 1);
		add(new UserPane(appPane), 1, 1);
	}

	private static class GridLoc {
		public int col, row, colSpan, rowSpan;

		public GridLoc(int c, int r, int cs, int rs) {
			col = c;
			row = r;
			colSpan = cs;
			rowSpan = rs;
		}
	}

	private static final Map<Class<? extends Node>, GridLoc> locations = new HashMap<>();

	static {
		locations.put(TopicPane.class, new GridLoc(0, 0, 2, 1));
		locations.put(MessagePane.class, new GridLoc(0, 1, 1, 1));
		locations.put(UserPane.class, new GridLoc(1, 1, 1, 1));
	}

	public <T extends Node> void setSubPane(Class<T> cl, T pane) {
		ObservableList<Node> nodes = getChildren();
		for(int k = nodes.size() - 1; k >= 0; k--) {
			Node n = nodes.get(k);
			if(n.getClass().equals(cl))
				nodes.remove(k);
		}

		GridLoc loc = locations.get(cl);
		add(pane, loc.col, loc.row, loc.colSpan, loc.rowSpan);
	}

}