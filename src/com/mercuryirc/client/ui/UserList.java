package com.mercuryirc.client.ui;

import com.mercuryirc.client.ui.model.User;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class UserList extends ListView<User> {
	private ObservableList<User> users;

	public UserList() {
		setId("user-list");
	}
}
