package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import com.mercuryirc.client.protocol.model.Server;
import com.mercuryirc.client.protocol.network.Connection;
import com.mercuryirc.client.ui.model.Message;
import com.mercuryirc.client.ui.model.Target;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class TabPane extends BorderPane {

	private TreeItem<Target> rootItem;
	private Map<Server, TreeItem<Target>> networkItems = new HashMap<>();
	private MultipleSelectionModel<TreeItem<Target>> selectionModel;

	public TabPane(final ApplicationPane parent) {
		getStylesheets().add(Mercury.class.getResource("./res/css/TabPane.css").toExternalForm());
		setMinWidth(200);
		VBox.setVgrow(this, Priority.ALWAYS);
		Label title = new Label("NETWORKS");
		title.setId("title-label");
		rootItem = new TreeItem<>();
		TreeView<Target> tree = new TreeView<>(rootItem);
		tree.setShowRoot(false);
		selectionModel = tree.getSelectionModel();
		selectionModel.selectedItemProperty().addListener(new ChangeListener<TreeItem<Target>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<Target>> observableValue, TreeItem<Target> targetTreeItem, TreeItem<Target> targetTreeItem2) {
				parent.setRight(targetTreeItem2.getValue().getChatPane());
			}
		});
		tree.setCellFactory(new Callback<TreeView<Target>, TreeCell<Target>>() {

			@Override
			public TreeCell<Target> call(TreeView<Target> targetTreeView) {
				return new TargetLabel();
			}

		});
		setTop(title);
		setCenter(tree);
	}

	public MultipleSelectionModel<TreeItem<Target>> getSelectionModel() {
		return selectionModel;
	}

	public TreeItem<Target> addNetwork(Connection connection) {
		TreeItem<Target> networkItem = new TreeItem<>(new Target(connection.getServer()));
		networkItem.setExpanded(true);
		networkItems.put(connection.getServer(), networkItem);
		rootItem.getChildren().add(networkItem);
		return networkItem;
	}

	public TreeItem<Target> addTarget(Connection connection, com.mercuryirc.client.protocol.model.Target target) {
		if (target instanceof Server) {
			return addNetwork(connection);
		}
		TreeItem<Target> networkItem;
		if (networkItems.containsKey(connection.getServer())) {
			networkItem = networkItems.get(connection.getServer());
		} else {
			networkItem = addNetwork(connection);
		}
		TreeItem<Target> targetItem = new TreeItem<>(new Target(target));
		networkItem.getChildren().add(targetItem);
		return targetItem;
	}

	public void addMessage(Connection connection, Message message) {
		addMessage(connection, connection.getServer().getChannel(message.getTarget()), message);
	}

	public void addMessage(Connection connection, com.mercuryirc.client.protocol.model.Target target, Message message) {
		getTargetItem(connection, target).getValue().getChatPane().messages().add(message);
	}

	public void addNick(Connection connection, com.mercuryirc.client.protocol.model.Target target, String nick) {
		/*ObservableList<User> users = getTargetItem(connection, target).getValue().getUserPane().getUsers();
		users.add(new User(nick));
		Collections.sort(users);*/
	}

	public void removeNick(Connection connection, com.mercuryirc.client.protocol.model.Target target, String nick) {
		//getTargetItem(connection, target).getValue().getUserPane().getUsers().remove(nick);
	}

	public void addNicks(Connection connection, com.mercuryirc.client.protocol.model.Target target, Set<String> nicks) {
		for (String nick : nicks) {
			addNick(connection, target, nick);
		}
	}

	private TreeItem<Target> getTargetItem(Connection connection, com.mercuryirc.client.protocol.model.Target target) {
		if (!networkItems.containsKey(connection.getServer())) {
			addNetwork(connection);
		}
		TreeItem<Target> targetItem = null;
		outer:
		for (TreeItem<Target> networkItem : networkItems.values()) {
			if (networkItem.getValue().getTarget().equals(connection.getServer())) {
				for (TreeItem<Target> _targetItem : networkItem.getChildren()) {
					if (_targetItem.getValue().getTarget().equals(target)) {
						targetItem = _targetItem;
						break outer;
					}
				}
			}
		}
		if (targetItem == null) {
			targetItem = addTarget(connection, target);
		}
		return targetItem;
	}

	private static class TargetLabel extends TreeCell<Target> {

		@Override
		protected void updateItem(Target item, boolean empty) {
			super.updateItem(item, empty);
			if (item != null) {
				Label label = new Label(item.getName());
				label.setId(item.getType().getStyle());
				setGraphic(label);
			}
			disclosureNodeProperty().set(new Region());
		}

	}

}