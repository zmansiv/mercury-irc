package com.mercuryirc.client.ui;

import com.mercuryirc.model.Channel;
import com.mercuryirc.model.Entity;
import com.mercuryirc.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserPane extends VBox {

	private static final char KEY_NORMAL_USER = 0;
	private static final Map<Character, String> rankNames = new HashMap<>();

	static {
		rankNames.put('~', "owners");
		rankNames.put('&', "admins");
		rankNames.put('@', "ops");
		rankNames.put('%', "halfops");
		rankNames.put('+', "voices");
	}

	private final ApplicationPane appPane;
	private final Channel channel;

	private final ListView<User> userListView;

	private final Comparator<User> USER_COMPARATOR = new Comparator<User>() {
		@Override
		public int compare(User o1, User o2) {
			int rankC = User.RANK_COMPARATOR.compare(o1.getChannelRank(channel), o2.getChannelRank(channel));
			return rankC == 0 ? o1.getName().compareToIgnoreCase(o2.getName()) : rankC * -1;
		}
	};

	public UserPane(ApplicationPane appPane, Entity entity) {
		this.appPane = appPane;
		setMinWidth(175);
		setMaxWidth(175);
		userListView = new ListView<>();
		if (entity instanceof Channel) {
			channel = (Channel) entity;
			userListView.getItems().addAll(channel.getUsers());
			sort();
			channel.getUsersProperty().addListener(new ListChangeListener<User>() {
				@Override
				public void onChanged(Change<? extends User> change) {
					while (change.next()) {
						if (change.wasAdded()) {
							userListView.getItems().addAll(change.getAddedSubList());
						}
						if (change.wasRemoved()) {
							userListView.getItems().removeAll(change.getRemoved());
						}
					}
					sort();
				}
			});
		} else {
			channel = null;
			if (entity instanceof User) {
				userListView.getItems().addAll(appPane.getConnection().getLocalUser(), (User) entity);
			}
		}
		VBox.setVgrow(userListView, Priority.ALWAYS);
		userListView.setId("user-list");
		userListView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
			@Override
			public ListCell<User> call(ListView<User> userListView) {
				return new UserListCell();
			}
		});
		getChildren().add(userListView);
	}

	public List<User> getUsers() {
		return userListView.getItems();
	}

	public void sort() {
		if (channel != null) {
			FXCollections.sort(userListView.getItems(), USER_COMPARATOR);
		}
	}

	/**
	 * does not work yet
	 */
	private class UserListCell extends ListCell<User> {

		@Override
		protected void updateItem(User user, boolean b) {
			super.updateItem(user, b);

			if (user == null)
				return;

			textProperty().bind(user.getNameProperty());
		}
	}

}