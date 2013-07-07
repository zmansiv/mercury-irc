package com.mercuryirc.client.ui;

import com.mercuryirc.client.ui.misc.FontAwesome;
import com.mercuryirc.misc.IrcUtils;
import com.mercuryirc.model.User;
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
	//private final Channel channel;

	private ListView<User> userList = new ListView<>();
	private UserButtonPane buttons = new UserButtonPane();

	private List<Character> ranksDisplayed = new ArrayList<>();

	/*private final Comparator<User> USER_COMPARATOR = new Comparator<User>() {
		@Override
		public int compare(User o1, User o2) {
			int rankC = User.RANK_COMPARATOR.compare(o1.getChannelRank(channel), o2.getChannelRank(channel));
			return rankC == 0 ? o1.getName().compareTo(o2.getName()) : rankC  * -1;
		}
	};*/

	public UserPane(ApplicationPane appPane) {
		this.appPane = appPane;
		//this.channel = channel;
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

	public void setUsers(Collection<User> users) {
		ObservableList<User> items = userList.getItems();

		ranksDisplayed.clear();
		items.setAll(users);

		//FXCollections.sort(items, USER_COMPARATOR);
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