package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import com.mercuryirc.client.MercuryCallback;
import com.mercuryirc.client.protocol.model.Server;
import com.mercuryirc.client.protocol.model.User;
import com.mercuryirc.client.protocol.network.Connection;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ApplicationPane extends HBox {

	private final TabPane tabPane;
	private final ContentPane contentPane;
	private final Connection connection;

	public ApplicationPane() {
		getStylesheets().add(Mercury.class.getResource("./res/css/ApplicationPane.css").toExternalForm());
		VBox.setVgrow(this, Priority.ALWAYS);
		getChildren().addAll(tabPane = new TabPane(this), contentPane = new ContentPane(this));
		connection = connectToIRC();
	}

	private Connection connectToIRC() {
		Server server = new Server("Rizon", "irc.rizon.net", 6667, true);
		User user = new User("Test|Mercury");
		user.setUserName("mercury");
		user.setRealName("Mercury IRC Client");
		Connection connection = new Connection(server, user, new MercuryCallback(this));
		connection.setAcceptAllSSLCerts(true);
		connection.connect();
		return connection;
	}

	public TabPane getTabPane() {
		return tabPane;
	}

	public ContentPane getContentPane() {
		return contentPane;
	}

}