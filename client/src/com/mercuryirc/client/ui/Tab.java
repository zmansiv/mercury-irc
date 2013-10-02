package com.mercuryirc.client.ui;

import com.mercuryirc.model.Entity;
import com.mercuryirc.network.Connection;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

public class Tab {

	private final ApplicationPane appPane;
	private final Connection connection;
	private final Entity entity;

	private final ContentPane contentPane;
	private ReadOnlyBooleanWrapper unreadProperty = new ReadOnlyBooleanWrapper(false);

	public Tab(ApplicationPane appPane, Connection connection, Entity entity) {
		this.appPane = appPane;
		this.connection = connection;
		this.entity = entity;

		this.contentPane = new ContentPane(connection, appPane, this);
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

	public boolean isUnread() {
		return unreadProperty.get();
	}

	public void setUnread(boolean unread) {
		unreadProperty.set(unread);
	}

	public ReadOnlyBooleanProperty getUnreadProperty() {
		return unreadProperty;
	}

}