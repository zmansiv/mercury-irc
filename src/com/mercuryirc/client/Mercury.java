package com.mercuryirc.client;

import com.mercuryirc.client.misc.Settings;
import com.mercuryirc.client.ui.ApplicationPane;
import com.mercuryirc.client.ui.ConnectDialog;
import com.mercuryirc.client.ui.TitlePane;
import com.mercuryirc.client.ui.misc.Tray;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Mercury extends Application {

	public static final String WEBSITE = "https://github.com/zmansiv/mercury-client-java";

	private static Stage stage;

	public static void main(String[] args) {
		Font.loadFont(Mercury.class.getResource("./res/fonts/font_awesome.ttf").toExternalForm(), 12);
		Font.loadFont(Mercury.class.getResource("./res/fonts/open_sans.ttf").toExternalForm(), 12);
		Font.loadFont(Mercury.class.getResource("./res/fonts/inconsolata.ttf").toExternalForm(), 12);
		Font.loadFont(Mercury.class.getResource("./res/fonts/inconsolata_bold.ttf").toExternalForm(), 12);
		Settings.init();
		Tray.init();
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		stage.setTitle("Mercury");
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.getIcons().add(new Image(Mercury.class.getResource("./res/images/icon32.png").toExternalForm()));
		VBox content = new VBox();
		content.getChildren().add(new TitlePane(stage));
		content.getChildren().add(new ApplicationPane());
		Scene scene = new Scene(content);
		scene.setFill(null);
		scene.getStylesheets().add(Mercury.class.getResource("./res/css/Mercury.css").toExternalForm());
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

		if(Settings.get("default-connection") == null)
			new ConnectDialog(stage);
	}

	private void setDefaultBounds() {
		Rectangle2D maximized = Screen.getPrimary().getVisualBounds();
		stage.setX(maximized.getMinX());
		stage.setY(maximized.getMinY());
		stage.setWidth(maximized.getWidth());
		stage.setHeight(maximized.getHeight());
	}

	public static Stage getStage() {
		return stage;
	}

}