package com.mercuryirc.client.ui;

import com.mercuryirc.client.protocol.model.Server;
import com.mercuryirc.client.protocol.model.Target;
import com.mercuryirc.client.protocol.network.Connection;
import com.mercuryirc.client.ui.model.Message;
import com.mercuryirc.client.ui.model.TargetTreeLabel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class TargetPanel extends SidePanel {

	private final ApplicationPane parent;
	private TreeItem<TargetTreeLabel> rootItem;
	private Map<Server, TreeItem<TargetTreeLabel>> networkItems = new HashMap<>();
	private MultipleSelectionModel<TreeItem<TargetTreeLabel>> selectionModel;

	public TargetPanel(ApplicationPane parent) {
		this.parent = parent;
		setId("left-side-panel");
		try {
			getStylesheets().add(new File("./res/css/TargetPanel.css").toURI().toURL().toExternalForm());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	protected void initialize(VBox contentBox) {
		rootItem = new TreeItem<>(TargetTreeLabel.NETWORKS);
		rootItem.setExpanded(true);
		final TreeView<TargetTreeLabel> tree = new TreeView<>(rootItem);
		tree.setShowRoot(true);
		final MultipleSelectionModel<TreeItem<TargetTreeLabel>> selectionModel = this.selectionModel = tree.getSelectionModel();
		tree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<TargetTreeLabel>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<TargetTreeLabel>> observableValue, TreeItem<TargetTreeLabel> targetTreeItem, TreeItem<TargetTreeLabel> targetTreeItem2) {
				if (targetTreeItem2 == null) {
					selectionModel.clearSelection();
					return;
				}
				if (targetTreeItem2.getValue().getType() == TargetTreeLabel.Type.NETWORKS) {
					selectionModel.select(targetTreeItem);
					return;
				}
				double[] dividers = parent.getDividerPositions();
				parent.getItems().setAll(TargetPanel.this, targetTreeItem2.getValue().getUserPanel(), targetTreeItem2.getValue().getMessagePanel());
				parent.setDividerPositions(dividers);
			}
		});
		contentBox.getChildren().add(tree);
		tree.setCellFactory(new Callback<TreeView<TargetTreeLabel>, TreeCell<TargetTreeLabel>>() {
			@Override
			public TreeCell<TargetTreeLabel> call(TreeView<TargetTreeLabel> p) {
				TreeCell<TargetTreeLabel> cell = new TreeCell<TargetTreeLabel>() {

					public void updateItem(TargetTreeLabel paramObject, boolean paramBoolean) {
						super.updateItem(paramObject, paramBoolean);
						setGraphic(paramObject);
					}

				};
				cell.disclosureNodeProperty().set(new Region());
				return cell;
			}
		});
	}

	public TreeItem<TargetTreeLabel> getRootItem() {
		return rootItem;
	}

	public void select(TreeItem<TargetTreeLabel> item) {
		selectionModel.select(item);
	}

	public TreeItem<TargetTreeLabel> addNetwork(Connection connection) {
		TreeItem<TargetTreeLabel> networkItem = new TreeItem<>(new TargetTreeLabel(connection, connection.getServer()));
		networkItem.setExpanded(true);
		networkItems.put(connection.getServer(), networkItem);
		rootItem.getChildren().add(networkItem);
		return networkItem;
	}

	public TreeItem<TargetTreeLabel> addTarget(Connection connection, Target target) {
		if (target instanceof Server) {
			return addNetwork(connection);
		}
		TreeItem<TargetTreeLabel> networkItem;
		if (networkItems.containsKey(connection.getServer())) {
			networkItem = networkItems.get(connection.getServer());
		} else {
			networkItem = addNetwork(connection);
		}
		TreeItem<TargetTreeLabel> targetItem = new TreeItem<>(new TargetTreeLabel(connection, target));
		networkItem.getChildren().add(targetItem);
		return targetItem;
	}

	public void addMessage(Connection connection, Message message) {
		addMessage(connection, connection.getServer().getChannel(message.getMessage().getTarget()), message);
	}

	public void addMessage(Connection connection, Target target, Message message) {
		getTargetItem(connection, target).getValue().getMessagePanel().messages().add(message);
	}

	public void addNick(Connection connection, Target target, String nick) {
		getTargetItem(connection, target).getValue().getUserPanel().getUsers().add(nick);
	}

	public void removeNick(Connection connection, Target target, String nick) {
		getTargetItem(connection, target).getValue().getUserPanel().getUsers().remove(nick);
	}

	public void setNicks(Connection connection, Target target, Set<String> nicks) {
		getTargetItem(connection, target).getValue().getUserPanel().getUsers().addAll(nicks);
	}

	private TreeItem<TargetTreeLabel> getTargetItem(Connection connection, Target target) {
		if (!networkItems.containsKey(connection.getServer())) {
			addNetwork(connection);
		}
		TreeItem<TargetTreeLabel> targetItem = null;
		outer:
		for (TreeItem<TargetTreeLabel> networkItem : networkItems.values()) {
			if (networkItem.getValue().getTarget().equals(connection.getServer())) {
				for (TreeItem<TargetTreeLabel> _targetItem : networkItem.getChildren()) {
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

}