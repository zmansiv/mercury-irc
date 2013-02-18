package com.mercuryirc.client;

import com.mercuryirc.client.protocol.model.Server;
import com.mercuryirc.client.protocol.network.Connection;
import com.mercuryirc.client.ui.ApplicationPane;
import com.mercuryirc.client.ui.TitlePane;
import com.mercuryirc.client.protocol.model.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Mercury extends Application {

	public static void main(String[] args) {
		Font.loadFont(Mercury.class.getResource("./res/fonts/font_awesome.ttf").toExternalForm(), 12);
		Font.loadFont(Mercury.class.getResource("./res/fonts/open_sans.ttf").toExternalForm(), 12);
		Font.loadFont(Mercury.class.getResource("./res/fonts/ubuntu_mono.ttf").toExternalForm(), 12);
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("Mercury");
		stage.initStyle(StageStyle.TRANSPARENT);
		VBox content = new VBox();
		ApplicationPane appPane = new ApplicationPane();

		content.getChildren().add(new TitlePane(stage));
		content.getChildren().add(appPane);
		Scene scene = new Scene(content, 1000, 650);
		scene.setFill(null);
		scene.getStylesheets().add(Mercury.class.getResource("./res/css/Mercury.css").toExternalForm());
		stage.setScene(scene);
		stage.show();

		connectToIRC(appPane);
	}

	private void connectToIRC(ApplicationPane appPane) {
		Server server = new Server("Rizon", "irc.rizon.net", 6667, true);

		User user = new User("Test|Mercury");
		user.setUserName("mercury");
		user.setRealName("Mercury IRC Client");

		Connection connection = new Connection(server, user, new MercuryCallback(appPane));
		connection.setAcceptAllSSLCerts(true);

		connection.connect();
	}

}