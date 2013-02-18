package com.mercuryirc.client.ui;

import javafx.scene.layout.VBox;

public class UserPane extends VBox {
	private UserList userList;
	private RightButtonBox buttons;

	public UserPane() {
		userList = new UserList();
		buttons = new RightButtonBox();

		getChildren().addAll(userList, buttons);
	}
}
