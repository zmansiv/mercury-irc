package com.mercuryirc.client.ui;

import com.mercuryirc.client.callback.InputCallbackImpl;
import com.mercuryirc.client.Mercury;
import com.mercuryirc.client.callback.OutputCallbackImpl;
import com.mercuryirc.model.Server;
import com.mercuryirc.model.User;
import com.mercuryirc.network.Connection;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ApplicationPane extends HBox {

	private final TabPane tabPane;
	private final VBox contentPaneContainer;
	private ContentPane contentPane;
	private final Connection connection;

	public ApplicationPane() {
		connection = connectToIRC();
		getStylesheets().add(Mercury.class.getResource("./res/css/ApplicationPane.css").toExternalForm());
		VBox.setVgrow(this, Priority.ALWAYS);
		HBox.setHgrow(this, Priority.ALWAYS);
		contentPaneContainer = new VBox();
		VBox.setVgrow(contentPaneContainer, Priority.ALWAYS);
		HBox.setHgrow(contentPaneContainer, Priority.ALWAYS);
		contentPaneContainer.getChildren().setAll(new VBox());
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
		getTabPane().select(getTabPane().get(connection, connection.getServer()));
	}

	private Connection connectToIRC() {
		Server server = new Server("Rizon", "irc.rizon.net", 6667, false);
		User user = new User(server, "Test|Mercury");
		user.setUserName("mercury");
		user.setRealName("Mercury IRC Client");
		Connection connection = new Connection(server, user, new InputCallbackImpl(this), new OutputCallbackImpl(this));
		connection.setAcceptAllSSLCerts(true);
		connection.connect();
		return connection;
	}

	public Connection getConnection() {
		return connection;
	}

	public TabPane getTabPane() {
		return tabPane;
	}

	public ContentPane getContentPane() {
		return contentPane;
	}

	public void setContentPane(ContentPane contentPane) {
		this.contentPane = contentPane;
		contentPaneContainer.getChildren().setAll(contentPane);
	}

}