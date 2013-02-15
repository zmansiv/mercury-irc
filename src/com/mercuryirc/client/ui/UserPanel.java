package com.mercuryirc.client.ui;

import com.mercuryirc.client.protocol.model.RankComparator;
import com.mercuryirc.client.protocol.model.Target;
import com.mercuryirc.client.protocol.network.Connection;
import com.mercuryirc.client.ui.model.TreeLabel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class UserPanel extends SidePanel {

	private TreeItem<TreeLabel> rootItem;
	private ObservableSet<String> users;
	private final Connection connection;
	private final Target target;

	public UserPanel(Connection connection, Target target) {
		this.connection = connection;
		this.target = target;
		try {
			getStylesheets().add(new File("./res/css/UserPanel.css").toURI().toURL().toExternalForm());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	protected void initialize(VBox contentBox) {
		rootItem = new TreeItem<>(TreeLabel.USERS);
		rootItem.setExpanded(true);
		final TreeView<TreeLabel> tree = new TreeView<>(rootItem);
		tree.setShowRoot(true);
		final MultipleSelectionModel<TreeItem<TreeLabel>> selectionModel = tree.getSelectionModel();
		selectionModel.selectedItemProperty().addListener(new ChangeListener<TreeItem<TreeLabel>>() {
			@Override
			public void changed(ObservableValue<? extends TreeItem<TreeLabel>> observableValue, TreeItem<TreeLabel> targetTreeItem, TreeItem<TreeLabel> targetTreeItem2) {
				if (targetTreeItem2 == null) {
					tree.getSelectionModel().clearSelection();
					return;
				}
				if (targetTreeItem2.getValue().getType() == TreeLabel.Type.USERS) {
					selectionModel.select(targetTreeItem);
				}
			}
		});
		contentBox.getChildren().add(tree);
		tree.setCellFactory(new Callback<TreeView<TreeLabel>, TreeCell<TreeLabel>>() {
			@Override
			public TreeCell<TreeLabel> call(TreeView<TreeLabel> p) {
				TreeCell<TreeLabel> cell = new TreeCell<TreeLabel>() {

					public void updateItem(TreeLabel item, boolean empty) {
						super.updateItem(item, empty);
						setGraphic(item);
					}

				};
				cell.disclosureNodeProperty().set(new Region());
				return cell;
			}
		});

		users = FXCollections.observableSet(new TreeSet<>(new RankComparator()));

		users.addListener(new SetChangeListener<String>() {
			@Override
			public void onChanged(Change<? extends String> change) {
				if (change.wasAdded()) {
					rootItem.getChildren().add(new TreeItem<>(new TreeLabel(change.getElementAdded(), connection, target, TreeLabel.Type.USER)));
				} else {
					Iterator<TreeItem<TreeLabel>> iter = rootItem.getChildren().iterator();
					while (iter.hasNext()) {
						if (iter.next().getValue().getText().equalsIgnoreCase(change.getElementRemoved())) {
							iter.remove();
						}
					}
				}
			}
		});
	}

	public TreeItem<TreeLabel> getRootItem() {
		return rootItem;
	}

	public ObservableSet<String> getUsers() {
		return users;
	}

}