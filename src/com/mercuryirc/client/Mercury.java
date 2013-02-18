package com.mercuryirc.client;

import com.mercuryirc.client.misc.Settings;
import com.mercuryirc.client.ui.ApplicationPane;
import com.mercuryirc.client.ui.TitlePane;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Mercury extends Application {

	private static Stage stage;

	public static void main(String[] args) {
		Font.loadFont(Mercury.class.getResource("./res/fonts/font_awesome.ttf").toExternalForm(), 12);
		Font.loadFont(Mercury.class.getResource("./res/fonts/open_sans.ttf").toExternalForm(), 12);
		Font.loadFont(Mercury.class.getResource("./res/fonts/ubuntu_mono.ttf").toExternalForm(), 12);
		Settings.init();
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		stage.setTitle("Mercury");
		stage.initStyle(StageStyle.TRANSPARENT);
		VBox content = new VBox();
		content.getChildren().add(new TitlePane(stage));
		content.getChildren().add(new ApplicationPane());
		Scene scene = new Scene(content);
		scene.setFill(null);
		scene.getStylesheets().add(Mercury.class.getResource("./res/css/Mercury.css").toExternalForm());
		stage.setScene(scene);
		String bounds = Settings.get("bounds");
		if (bounds == null) {
			Rectangle2D maximized = Screen.getPrimary().getVisualBounds();
			stage.setX(maximized.getMinX());
			stage.setY(maximized.getMinY());
			stage.setWidth(maximized.getWidth());
			stage.setHeight(maximized.getHeight());
		} else {
			String[] bounds_ = bounds.split(" ");
			stage.setX(Double.parseDouble(bounds_[0]));
			stage.setY(Double.parseDouble(bounds_[1]));
			stage.setWidth(Double.parseDouble(bounds_[2]));
			stage.setHeight(Double.parseDouble(bounds_[3]));
		}
		stage.show();
	}

	public static Stage getStage() {
		return stage;
	}

}