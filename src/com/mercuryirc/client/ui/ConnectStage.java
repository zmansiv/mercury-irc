package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class ConnectStage extends Stage {

	public ConnectStage(final Window owner) {
		super();
		setTitle("Mercury // connect");
		initModality(Modality.WINDOW_MODAL);

		initOwner(owner);
		initStyle(StageStyle.TRANSPARENT);

		getIcons().add(new Image(Mercury.class.getResource("./res/images/icon32.png").toExternalForm()));
		VBox content = new VBox();
		content.getChildren().add(new ConnectPane());
		Scene scene = new Scene(content);
		scene.setFill(null);
		scene.getStylesheets().add(Mercury.class.getResource("./res/css/Mercury.css").toExternalForm());
		scene.getStylesheets().add(Mercury.class.getResource("./res/css/ConnectStage.css").toExternalForm());
		setScene(scene);
		show();

		owner.getScene().getRoot().setEffect(new BoxBlur());
	}

}