package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import com.mercuryirc.client.ui.misc.FontAwesome;
import com.mercuryirc.client.ui.model.MessageRow;
import com.mercuryirc.model.Channel;
import com.mercuryirc.model.Entity;
import com.mercuryirc.model.Message;
import com.mercuryirc.model.Server;
import com.mercuryirc.model.User;
import com.mercuryirc.network.Connection;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TabPane extends VBox {

	private final ApplicationPane appPane;
	private final ListView<Tab> tabList;
	private final MultipleSelectionModel<Tab> selectionModel;

	public TabPane(ApplicationPane appPane) {
		this.appPane = appPane;
		getStylesheets().add(Mercury.class.getResource("/res/css/TabPane.css").toExternalForm());
		setMinWidth(200);
		setMaxWidth(200);
		final TabButtonPane buttonPane = new TabButtonPane();
		final VBox tabListBox = new VBox();
		tabListBox.getStyleClass().add("dark-pane");
		tabListBox.setId("tab-list");
		setVgrow(tabListBox, Priority.ALWAYS);
		tabList = new ListView<>();
		setVgrow(tabList, Priority.ALWAYS);
		selectionModel = tabList.getSelectionModel();
		selectionModel.selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
				if (number2 == 0) {
					tabList.getStyleClass().add("first");
				} else {
					tabList.getStyleClass().remove("first");
				}
			}
		});
		selectionModel.selectedItemProperty().addListener(new TabClickedListener());
		tabList.setCellFactory(new Callback<ListView<Tab>, ListCell<Tab>>() {
			public ListCell<Tab> call(ListView<Tab> tabListView) {
				return new TabCell();
			}
		});
		tabListBox.getChildren().add(tabList);
		getChildren().addAll(buttonPane, tabListBox);
	}


	public void addUserStatusMessage(User source, Message message, MessageRow.Type type, TabAction action) {
		for (Tab tab : getItems()) {
			if (tab.getEntity().equals(source) || (tab.getEntity() instanceof Channel && ((Channel) tab.getEntity()).getUsers().contains(source))) {
				tab.getContentPane().getMessagePane().addRow(new MessageRow(message, type));
				if (action != null) {
					action.process(tab);
				}
			}
		}
	}

	public void addTargetedMessage(Connection connection, Message message, MessageRow.Type type) {
		Entity target = message.getTarget();
		if (target.equals(connection.getLocalUser())) {
			target = message.getSource();
		}
		Tab tab = null;
		for (Tab _tab : getItems()) {
			if (_tab.getEntity().equals(target)) {
				tab = _tab;
				break;
			}
		}
		if (tab == null) {
			if (type == MessageRow.Type.PART && message.getSource().equals(connection.getLocalUser())) {
				return;
			}
			tab = create(connection, target);
		}
		tab.getContentPane().getMessagePane().addRow(new MessageRow(message, type));
	}

	public void addUntargetedMessage(Connection connection, Message message, MessageRow.Type type) {
		Tab tab = getSelected();
		if (tab == null || !tab.getConnection().equals(connection)) {
			tab = null;
			for (Tab _tab : getItems()) {
				if (_tab.getConnection().equals(connection)) {
					tab = _tab;
					break;
				}
			}
			if (tab == null) {
				tab = create(connection, connection.getServer());
			}
		}
		tab.getContentPane().getMessagePane().addRow(new MessageRow(message, type));
	}

	public Collection<Tab> getItems() {
		return tabList.getItems();
	}

	public Tab create(Connection connection, Entity entity) {
		return create(connection, entity, false);
	}

	public Tab create(Connection connection, Entity entity, boolean select) {
		Tab tab = new Tab(appPane, connection, entity);
		List<Tab> tabs = tabList.getItems();
		boolean added = false;
		for (int i = tabs.size() - 1; i >= 0; i--) {
			if (tabs.get(i).getConnection().equals(connection)) {
				tabs.add(i + 1, tab);
				added = true;
				break;
			}
		}
		if (!added) {
			tabs.add(tab);
		}
		//javafx webview bug workaround -_-
		Tab selected = getSelected();
		select(tab);
		if (!select) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			if (selected != null) {
				select(selected);
			}
		}
		return tab;
	}

	public Tab get(Connection connection, Entity entity) {
		for (Tab t : tabList.getItems()) {
			if (t.getConnection().equals(connection) && t.getEntity().equals(entity)) {
				return t;
			}
		}
		return create(connection, entity);
	}

	public Tab getSelected() {
		return selectionModel.getSelectedItem();
	}

	public void select(Tab tab) {
		selectionModel.select(tab);
	}

	public void selectNext() {
		if (selectionModel.getSelectedIndex() == tabList.getItems().size() - 1) {
			selectionModel.selectFirst();
		} else {
			selectionModel.selectNext();
		}
	}

	public void selectPrevious() {
		if (selectionModel.getSelectedIndex() == 0) {
			selectionModel.selectLast();
		} else {
			selectionModel.selectPrevious();
		}
	}

	public void close(Tab tab) {
		close(tab, true);
	}

	public void close(Tab tab, boolean action) {
		if (getSelected().equals(tab)) {
			if (selectionModel.getSelectedIndex() == tabList.getItems().size()) {
				selectPrevious();
			} else {
				selectNext();
			}
		}
		{
			Iterator<Tab> it = tabList.getItems().iterator();
			while (it.hasNext()) {
				Tab _tab = it.next();
				if (_tab.equals(tab)) {
					it.remove();
					if (action) {
						if (tab.getEntity() instanceof Channel) {
							appPane.getContentPane().getConnection().part((Channel) tab.getEntity());
						} else if (tab.getEntity() instanceof Server) {
							appPane.getContentPane().getConnection().quit("");
						}
					}
					break;
				}
			}
		}
		if (tab.getEntity() instanceof Server) {
			Iterator<Tab> it = tabList.getItems().iterator();
			while (it.hasNext()) {
				Tab _tab = it.next();
				if (_tab.getConnection().equals(tab.getConnection())) {
					close(_tab);
				}
			}
		}
	}

	private class TabClickedListener implements ChangeListener<Tab> {

		public void changed(ObservableValue<? extends Tab> ov, Tab oldTab, Tab newTab) {
			if (newTab == null) {
				appPane.setContentPane(null);
				return;
			}
			newTab.setUnread(false);
			appPane.setContentPane(newTab.getContentPane());
			final TextField inputField = appPane.getContentPane().getMessagePane().getInputPane().getInputField();
			Platform.runLater(new Runnable() {
				public void run() {
					inputField.requestFocus();
					inputField.positionCaret(inputField.getText().length());
					inputField.deselect();
				}
			});
		}

	}

	private class TabCell extends ListCell<Tab> {

		@Override
		protected void updateItem(final Tab tab, boolean empty) {
			super.updateItem(tab, empty);
			if (tab != null) {
				tab.getUnreadProperty().addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
						if (newValue) {
							getStyleClass().add("unread");
						} else {
							getStyleClass().remove("unread");
						}
					}
				});
				Entity entity = tab.getEntity();
				setPrefHeight(50);
				if (entity instanceof Server) {
					setPrefHeight(60);
					Label net = new Label("network");
					net.getStyleClass().add("network");
					VBox box = new VBox();
					Label name = new Label(entity.getName());
					name.getStyleClass().add("network-name");
					box.getChildren().addAll(net, name);
					setGraphic(box);
				} else if (entity instanceof Channel) {
					setGraphic(FontAwesome.createIcon(FontAwesome.COMMENTS));
					setText(entity.getName().substring(1));
				} else if (entity instanceof User) {
					setGraphic(FontAwesome.createIcon(FontAwesome.USER));
					setText(entity.getName());
				}
			}
		}

	}

	private class TabButtonPane extends HBox {

		public TabButtonPane() {
			super(10);
			setAlignment(Pos.CENTER);
			getStyleClass().add("dark-pane");
			setId("tab-button-pane");
			setMinHeight(85);
			Button newButton = FontAwesome.createIconButton(FontAwesome.PLUS, "new", true, "green");
			newButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					new ConnectStage(Mercury.getStage());
				}
			});
			Button websiteButton = FontAwesome.createIconButton(FontAwesome.GLOBE, "", true, null);
			websiteButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					try {
						Desktop.getDesktop().browse(new URI(Mercury.WEBSITE));
					} catch (URISyntaxException | IOException e) {
						e.printStackTrace();
					}
				}
			});
			Button settingsButton = FontAwesome.createIconButton(FontAwesome.COG, "", true, null);
			getChildren().addAll(newButton, websiteButton, settingsButton);
		}

	}

	public static abstract class TabAction {

		public abstract void process(Tab tab);

	}

}