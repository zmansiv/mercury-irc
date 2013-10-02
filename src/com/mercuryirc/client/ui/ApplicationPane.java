package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ApplicationPane extends HBox {

	private final TabPane tabPane;
	private final VBox contentPaneContainer;
	private ContentPane contentPane;

	public ApplicationPane() {
		getStylesheets().add(Mercury.class.getResource("/res/css/ApplicationPane.css").toExternalForm());
		VBox.setVgrow(this, Priority.ALWAYS);
		HBox.setHgrow(this, Priority.ALWAYS);
		contentPaneContainer = new VBox();
		VBox.setVgrow(contentPaneContainer, Priority.ALWAYS);
		HBox.setHgrow(contentPaneContainer, Priority.ALWAYS);
		VBox vbox = new VBox();
		VBox.setVgrow(vbox, Priority.ALWAYS);
		HBox.setHgrow(vbox, Priority.ALWAYS);
		vbox.getStyleClass().add("dark-pane");
		contentPaneContainer.getChildren().setAll(vbox);
		getChildren().addAll(tabPane = new TabPane(this), contentPaneContainer);
		setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (keyEvent.isControlDown()) {
					switch (keyEvent.getCode()) {
						case W:
							tabPane.close(tabPane.getSelected());
							break;
						case TAB:
							if (keyEvent.isShiftDown()) {
								tabPane.selectPrevious();
							} else {
								tabPane.selectNext();
							}
							break;
					}
				}
			}
		});
	}

	public TabPane getTabPane() {
		return tabPane;
	}

	public ContentPane getContentPane() {
		return contentPane;
	}

	public void setContentPane(ContentPane contentPane) {
		this.contentPane = contentPane;
		if (contentPane == null) {
			contentPaneContainer.getChildren().clear();
		} else {
			contentPaneContainer.getChildren().setAll(contentPane);
		}
	}

}