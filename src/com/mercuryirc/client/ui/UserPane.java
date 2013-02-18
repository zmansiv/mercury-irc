package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import com.mercuryirc.client.ui.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.LinkedList;

public class UserPane extends VBox {

	private ObservableList<User> users;

	public UserPane() {
		getStylesheets().add(Mercury.class.getResource("./res/css/UserPane.css").toExternalForm());
		setMinWidth(175);
		VBox.setVgrow(this, Priority.ALWAYS);
		Label title = new Label("USERS");
		title.setId("title-label");
		ListView<User> userList = new ListView<>(users = FXCollections.observableList(new LinkedList<User>()));
		userList.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
			@Override
			public ListCell<User> call(ListView<User> userListView) {
				return new UserLabel();
			}
		});
		getChildren().addAll(title, userList);
	}

	public ObservableList<User> getUsers() {
		return users;
	}

	private static class UserLabel extends ListCell<User> {

		@Override
		protected void updateItem(User item, boolean empty) {
			super.updateItem(item, empty);
			if (item != null) {
				Label label = new Label(item.getName());
				label.setId("user-label");
				setGraphic(label);
			}
		}

	}

}