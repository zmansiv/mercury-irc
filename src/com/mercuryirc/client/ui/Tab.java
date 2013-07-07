package com.mercuryirc.client.ui;

import com.mercuryirc.model.Entity;
import com.mercuryirc.network.Connection;

public class Tab {

	private final Connection connection;
	private final Entity entity;

	private final ContentPane contentPane;

	public Tab(ApplicationPane appPane, Connection connection, Entity entity) {
		this.connection = connection;
		this.entity = entity;

		this.contentPane = new ContentPane(appPane, this);
	}

	public Connection getConnection() {
		return connection;
	}

	public Entity getEntity() {
		return entity;
	}

	public ContentPane getContentPane() {
		return contentPane;
	}

}