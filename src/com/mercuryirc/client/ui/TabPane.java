package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Server;
import com.mercuryirc.client.protocol.model.Target;
import com.mercuryirc.client.protocol.model.User;
import com.mercuryirc.client.ui.misc.FontAwesome;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class TabPane extends VBox {

	private final ApplicationPane appPane;
	private final ListView<Tab> tabList;
	private final MultipleSelectionModel<Tab> selectionModel;

	public TabPane(ApplicationPane appPane) {
		this.appPane = appPane;
		getStylesheets().add(Mercury.class.getResource("./res/css/TabPane.css").toExternalForm());
		setMinWidth(200);
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
					tabListBox.setId("tab-list-alt");
					tabList.getStyleClass().add("first");
				} else {
					tabListBox.setId("tab-list");
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

	public Tab addTab(Target target) {
		Tab t = new Tab(appPane, target);
		tabList.getItems().add(t);
		return t;
	}

	public Tab getTab(Target target) {
		for (Tab t : tabList.getItems()) {
			if (t.getTarget().equals(target))
				return t;
		}
		return addTab(target);
	}

	public void selectTab(Tab tab) {
		selectionModel.select(tab);
	}

	private class TabClickedListener implements ChangeListener<Tab> {

		public void changed(ObservableValue<? extends Tab> ov, Tab oldTab, Tab newTab) {
			appPane.getContentPane().setSubPane(TopicPane.class, newTab.getTopicPane());
			appPane.getContentPane().setSubPane(MessagePane.class, newTab.getMessagePane());
			appPane.getContentPane().setSubPane(UserPane.class, newTab.getUserPane());
		}

	}

	private class TabCell extends ListCell<Tab> {

		@Override
		protected void updateItem(Tab tab, boolean empty) {
			super.updateItem(tab, empty);
			if (tab != null) {
				Target target = tab.getTarget();
				setMinHeight(50);
				setMaxHeight(50);
				if (target instanceof Server) {
					setMinHeight(60);
					setMaxHeight(60);
					Label net = new Label("network");
					net.getStyleClass().add("network");

					VBox box = new VBox();
					Label name = new Label(target.getName());
					box.getChildren().addAll(net, name);

					setGraphic(box);
				} else if (target instanceof Channel) {
					setGraphic(FontAwesome.createIcon(FontAwesome.COMMENTS));
					setText(target.getName().substring(1));
				} else if (target instanceof User) {
					setGraphic(FontAwesome.createIcon(FontAwesome.USER));
					setText(target.getName());
				}
				setMinHeight(50);
				setMaxHeight(50);
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
			getChildren().addAll(FontAwesome.createIconButton(FontAwesome.PLUS, "new", "green"), FontAwesome.createIconButton(FontAwesome.GLOBE), FontAwesome.createIconButton(FontAwesome.COG));
		}

	}

}