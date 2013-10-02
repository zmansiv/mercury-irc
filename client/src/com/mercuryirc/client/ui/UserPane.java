package com.mercuryirc.client.ui;

import com.mercuryirc.model.Channel;
import com.mercuryirc.model.Entity;
import com.mercuryirc.model.Mode;
import com.mercuryirc.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.Comparator;
import java.util.List;

public class UserPane extends VBox {

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
		userListView.setMinWidth(175);
		userListView.setMaxWidth(175);
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
				userListView.getItems().addAll(appPane.getContentPane().getConnection().getLocalUser(), (User) entity);
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

	private class UserListCell extends ListCell<User> {

		@Override
		protected void updateItem(User user, boolean b) {
			super.updateItem(user, b);
			if (user == null) {
				return;
			}
			setMinWidth(175);
			setMaxWidth(175);
			setId("user-cell");
			HBox hbox = new HBox();
			if (UserPane.this.channel != null) {
				boolean rankLabeledCell = false;
				Mode.Type lastRank = null;
				for (User _user : UserPane.this.getUsers()) {
					if (_user.equals(user)) {
						Mode.Type rank = user.getChannelRank(UserPane.this.channel);
						if (rank != lastRank) {
							if (lastRank != null) {
								setId("user-rank-cell");
							}
							if (rank != null) {
								String rankName = null;
								switch (rank) {
									case OWNER:
										rankName = "Owner";
										break;
									case PROTECT:
										rankName = "Admin";
										break;
									case OP:
										rankName = "Op";
										break;
									case HALFOP:
										rankName = "Halfop";
										break;
									case VOICE:
										rankName = "Voice";
								}
								Label ranklabel = new Label(rankName);
								ranklabel.setId("rank-label");
								ranklabel.setMinWidth(60);
								ranklabel.setMaxWidth(60);
								hbox.getChildren().add(ranklabel);
								rankLabeledCell = true;
							}
						}
						break;
					}
					lastRank = _user.getChannelRank(UserPane.this.channel);
				}
				if (!rankLabeledCell) {
					Region spacer = new Region();
					spacer.setMinWidth(60);
					spacer.setMaxWidth(60);
					hbox.getChildren().add(spacer);
				}
			}
			Label nickLabel = new Label();
			nickLabel.setId("user-label");
			nickLabel.setTextOverrun(OverrunStyle.ELLIPSIS);
			nickLabel.textProperty().bind(user.getNameProperty());
			hbox.getChildren().add(nickLabel);
			setGraphic(hbox);
		}
	}

}