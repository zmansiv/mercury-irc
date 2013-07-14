package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import com.mercuryirc.client.misc.Settings;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.*;

public class ConnectDialog {
	private Stage stage;

	public ConnectDialog(final Window owner) {
		stage = new Stage();
		stage.setTitle("Mercury // connect");
		stage.initModality(Modality.WINDOW_MODAL);

		stage.initOwner(owner);
		stage.initStyle(StageStyle.TRANSPARENT);

		// todo: abstract all of this into a MercuryWindow base class that provides the
		// styling and title bar
		stage.getIcons().add(new Image(Mercury.class.getResource("./res/images/icon32.png").toExternalForm()));
		VBox content = new VBox();
		content.getChildren().add(new TitlePane(stage));
		content.getChildren().add(new ConnectPane());
		Scene scene = new Scene(content);
		scene.setFill(null);
		scene.getStylesheets().add(Mercury.class.getResource("./res/css/Mercury.css").toExternalForm());
		stage.setScene(scene);
		stage.show();

		owner.getScene().getRoot().setEffect(new BoxBlur());
	}

	public void onSave() {
		// todo real values
		String name = "Rizon";
		String server = "irc.rizon.net";
		int port = 6667;
		boolean ssl = false;
		String nick = "Test|Mercury";
		String user = "mercury";

		Settings.set("default-connection",
			String.format("%s %s %d %b %s %s", name, server, port, ssl, nick, user));
	}
}
