package com.mercuryirc.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.mercuryirc.client.callback.CallbackImpl;
import com.mercuryirc.client.misc.Settings;
import com.mercuryirc.client.misc.Tray;
import com.mercuryirc.client.ui.ApplicationPane;
import com.mercuryirc.client.ui.ConnectStage;
import com.mercuryirc.client.ui.Tab;
import com.mercuryirc.client.ui.TitlePane;
import com.mercuryirc.model.Channel;
import com.mercuryirc.model.Entity;
import com.mercuryirc.model.Server;
import com.mercuryirc.model.User;
import com.mercuryirc.network.Connection;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.LinkedList;
import java.util.List;

public class Mercury extends Application {

	public static final String WEBSITE = "https://github.com/zmansiv/mercury-client-java";

	private static Stage stage;
	private static ApplicationPane appPane;
	private static CallbackImpl callback;
	private static final List<Connection> connections = new LinkedList<>();

	public static void main(String[] args) {
		Font.loadFont(Mercury.class.getResource("/res/fonts/font_awesome.ttf").toExternalForm(), 12);
		Font.loadFont(Mercury.class.getResource("/res/fonts/open_sans.ttf").toExternalForm(), 12);
		Font.loadFont(Mercury.class.getResource("/res/fonts/inconsolata.ttf").toExternalForm(), 12);
		Font.loadFont(Mercury.class.getResource("/res/fonts/inconsolata_bold.ttf").toExternalForm(), 12);
		Settings.init();
		Tray.init();
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		stage.setTitle("Mercury");
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.getIcons().add(new Image(Mercury.class.getResource("/res/images/icon32.png").toExternalForm()));
		VBox content = new VBox();
		content.getChildren().add(new TitlePane(stage));
		content.getChildren().add(appPane = new ApplicationPane());
		callback = new CallbackImpl(appPane);
		Scene scene = new Scene(content);
		scene.setFill(null);
		scene.getStylesheets().add(Mercury.class.getResource("/res/css/Mercury.css").toExternalForm());
		stage.setScene(scene);
		String bounds = Settings.get("bounds");
		if (bounds == null) {
			setDefaultBounds();
		} else {
			String[] bounds_ = bounds.split(" ");
			stage.setX(Double.parseDouble(bounds_[0]));
			stage.setY(Double.parseDouble(bounds_[1]));
			stage.setWidth(Double.parseDouble(bounds_[2]));
			stage.setHeight(Double.parseDouble(bounds_[3]));
			//check if window was initialized offscreen
			//e.g. if someone used mercury with a secondary monitor that is now disconnected
			Screen screen = null;
			for (Screen _screen : Screen.getScreens()) {
				if (_screen.getBounds().contains(stage.getX(), stage.getY())) {
					screen = _screen;
					break;
				}
			}
			if (screen == null) {
				setDefaultBounds();
			}
		}
		stage.show();

		loadConnections();
	}

	private void setDefaultBounds() {
		Rectangle2D maximized = Screen.getPrimary().getVisualBounds();
		stage.setX(maximized.getMinX());
		stage.setY(maximized.getMinY());
		stage.setWidth(maximized.getWidth());
		stage.setHeight(maximized.getHeight());
	}

	public static void saveConnections() {
		Gson gson = new Gson();
		JsonObject json = new JsonObject();
		JsonArray connectionsArray = new JsonArray();
		for (Connection conn : connections) {
			JsonObject connection = new JsonObject();
			connection.addProperty("name", conn.getServer().getName());
			connection.addProperty("host", conn.getServer().getHost());
			connection.addProperty("port", conn.getServer().getPort());
			connection.addProperty("password", conn.getServer().getPassword());
			connection.addProperty("ssl", conn.getServer().isSsl());
			JsonObject user = new JsonObject();
			user.addProperty("nick", conn.getLocalUser().getName());
			user.addProperty("username", conn.getLocalUser().getUserName());
			user.addProperty("realname", conn.getLocalUser().getRealName());
			String nspw = conn.getLocalUser().getNickservPassword();
			if (nspw != null) {
				user.addProperty("password", nspw);
			}
			JsonArray channels = new JsonArray();
			for (Tab tab : appPane.getTabPane().getItems()) {
				if (tab.getConnection().equals(conn)) {
					Entity entity = tab.getEntity();
					if (entity instanceof Channel) {
						channels.add(new JsonPrimitive(entity.getName()));
					}
				}
			}
			user.add("channels", channels);
			connection.add("user", user);
			connectionsArray.add(connection);
		}
		json.add("connections", connectionsArray);
		Settings.set("connections", gson.toJson(json));
	}

	private void loadConnections() {
		if (Settings.get("connections") == null) {
			new ConnectStage(stage);
			return;
		}
		String connectionsString = Settings.get("connections");
		JsonParser parser = new JsonParser();
		Gson gson = new Gson();
		JsonObject connectionsJson = parser.parse(connectionsString).getAsJsonObject();
		JsonElement obj = connectionsJson.get("connections");
		JsonArray connectionsArray = obj.getAsJsonArray();
		if (connectionsArray.size() == 0) {
			new ConnectStage(stage);
			return;
		}
		for (JsonElement connectionElement : connectionsArray) {
			JsonObject connectionObject = connectionElement.getAsJsonObject();
			String name = connectionObject.get("name").getAsString();
			String hostname = connectionObject.get("host").getAsString();
			int port = connectionObject.get("port").getAsInt();
			String password = null;
			if (connectionObject.has("password")) {
				password = connectionObject.get("password").getAsString();
			}
			boolean ssl = connectionObject.get("ssl").getAsBoolean();
			Server server = new Server(name, hostname, port, password, ssl);
			JsonObject userObject = connectionObject.get("user").getAsJsonObject();
			String nick = userObject.get("nick").getAsString();
			String username = userObject.get("username").getAsString();
			String realname = userObject.get("realname").getAsString();
			String nspw = null;
			if (userObject.has("password")) {
				nspw = userObject.get("password").getAsString();
			}
			JsonArray channels = userObject.getAsJsonArray("channels");
			String[] autoChannels = gson.fromJson(channels.toString(), new TypeToken<String[]>() {
			}.getType());
			User user = new User(server, nick, username, realname);
			user.setNickservPassword(nspw);
			user.setAutojoinChannels(autoChannels);
			connect(server, user);
		}
	}

	public static void connect(Server server, User user) {
		Connection connection = new Connection(server, user, callback);
		connection.setAcceptAllSSLCerts(true);
		connection.connect();
		appPane.getTabPane().create(connection, connection.getServer(), true);
		connections.add(connection);
	}

	public static Stage getStage() {
		return stage;
	}

}