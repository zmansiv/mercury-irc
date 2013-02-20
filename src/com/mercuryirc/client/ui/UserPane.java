package com.mercuryirc.client.ui;

import com.mercuryirc.client.protocol.misc.IrcUtils;
import com.mercuryirc.client.ui.misc.FontAwesome;
import com.mercuryirc.client.ui.model.UserRow;
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

import java.util.*;

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

	private ListView<UserRow> userList = new ListView<>();
	private UserButtonPane buttons = new UserButtonPane();

	private List<Character> ranksDisplayed = new ArrayList<>();

	public UserPane(ApplicationPane appPane) {
		this.appPane = appPane;
		setMinWidth(175);
		VBox.setVgrow(userList, Priority.ALWAYS);
		userList.setId("user-list");
		userList.setCellFactory(new Callback<ListView<UserRow>, ListCell<UserRow>>() {
			@Override
			public ListCell<UserRow> call(ListView<UserRow> userListView) {
				return new UserListCell();
			}
		});
		getChildren().addAll(userList, buttons);
	}

	public List<UserRow> getUsers() {
		return userList.getItems();
	}

	public void setUsers(Collection<String> users) {
		List<UserRow> rows = new ArrayList<>();
		for(String u : users)
			rows.add(new UserRow(u));

		ObservableList<UserRow> items = userList.getItems();

		ranksDisplayed.clear();
		items.clear();
		items.addAll(rows);

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

	/** does not work yet */
	private class UserListCell extends ListCell<UserRow> {
		@Override
		protected void updateItem(UserRow user, boolean b) {
			super.updateItem(user, b);

			if(user == null)
				return;

			char ch = user.getName().charAt(0);

			if(IrcUtils.isRank(ch)) {
				// only do first time
				if(!ranksDisplayed.contains(ch)) {
					setGraphic(new Label(rankNames.get(ch)));
					ranksDisplayed.add(ch);
				}
				setText(user.getName().substring(1));
			} else {
				if(!ranksDisplayed.contains(KEY_NORMAL_USER)) {
					setGraphic(new Label("users"));
					ranksDisplayed.add(KEY_NORMAL_USER);
				}
				setText(user.getName());
			}
		}
	}
}