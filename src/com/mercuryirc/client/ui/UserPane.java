package com.mercuryirc.client.ui;

import com.mercuryirc.client.protocol.misc.IrcUtils;
import com.mercuryirc.client.protocol.model.Target;
import com.mercuryirc.client.protocol.model.User;
import com.mercuryirc.client.ui.misc.FontAwesome;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
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

	private ListView<User> userList = new ListView<>();
	private UserButtonPane buttons = new UserButtonPane();

	private List<Character> ranksDisplayed = new ArrayList<>();

	public UserPane(ApplicationPane appPane) {
		this.appPane = appPane;
		setMinWidth(175);
		VBox.setVgrow(userList, Priority.ALWAYS);
		userList.setId("user-list");
		userList.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
			@Override
			public ListCell<User> call(ListView<User> userListView) {
				return new UserListCell();
			}
		});
		getChildren().addAll(userList, buttons);
	}

	public List<User> getUsers() {
		return userList.getItems();
	}

	public void setUsers(Collection<String> users) {
		List<User> rows = new LinkedList<>();
		for (String u : users) {
			Target target = appPane.getConnection().resolveTarget(IrcUtils.isRank(u.charAt(0)) ? u.substring(1) : u);
			if (target instanceof User) {
				rows.add((User) target);
			}
		}

		ObservableList<User> items = userList.getItems();

		ranksDisplayed.clear();
		items.setAll(rows);

		FXCollections.sort(items);
	}

	private class UserButtonPane extends HBox {

		public UserButtonPane() {
			super(10);
			getStyleClass().add("dark-pane");
			setId("user-button-pane");
			setAlignment(Pos.CENTER);
			setMinHeight(65);
			setMaxHeight(65);
			getChildren().addAll(FontAwesome.createIconButton(FontAwesome.INFO), FontAwesome.createIconButton(FontAwesome.ENVELOPE), FontAwesome.createIconButton(FontAwesome.LEGAL));
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

			char ch = user.getName().charAt(0);

			if (IrcUtils.isRank(ch)) {
				// only do first time
				if (!ranksDisplayed.contains(ch)) {
					setGraphic(new Label(rankNames.get(ch)));
					ranksDisplayed.add(ch);
				}
				setText(user.getName().substring(1));
			} else {
				if (!ranksDisplayed.contains(KEY_NORMAL_USER)) {
					setGraphic(new Label("users"));
					ranksDisplayed.add(KEY_NORMAL_USER);
				}
				setText(user.getName());
			}
		}
	}
}