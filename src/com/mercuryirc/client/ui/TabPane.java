package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import com.mercuryirc.client.ui.misc.FontAwesome;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TabPane extends VBox {

	private final ApplicationPane appPane;

	public TabPane(ApplicationPane appPane) {
		this.appPane = appPane;
		getStylesheets().add(Mercury.class.getResource("./res/css/TabPane.css").toExternalForm());
		setMinWidth(200);
		final TabButtonPane buttonPane = new TabButtonPane();
		final VBox tabListBox = new VBox();
		tabListBox.getStyleClass().add("dark-pane");
		tabListBox.setId("tab-list");
		setVgrow(tabListBox, Priority.ALWAYS);
		final ListView<Node> tabList = new ListView<>();
		setVgrow(tabList, Priority.ALWAYS);
		tabList.getItems().add(new Label("cafebabe"));
		tabList.getItems().add(new Label("rsbuddy"));
		tabListBox.getChildren().add(tabList);
		tabList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
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
		getChildren().addAll(buttonPane, tabListBox);
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