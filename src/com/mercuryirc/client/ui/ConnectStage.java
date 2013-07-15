package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class ConnectStage extends Stage {

	private final Window owner;

	public ConnectStage(final Window owner) {
		super();
		this.owner = owner;
		setTitle("Mercury // connect");
		initModality(Modality.WINDOW_MODAL);

		initOwner(owner);
		initStyle(StageStyle.TRANSPARENT);

		getIcons().add(new Image(Mercury.class.getResource("./res/images/icon32.png").toExternalForm()));
		Scene scene = new Scene(new ConnectPane(this));
		scene.setFill(null);
		scene.getStylesheets().add(Mercury.class.getResource("./res/css/Mercury.css").toExternalForm());
		scene.getStylesheets().add(Mercury.class.getResource("./res/css/ConnectStage.css").toExternalForm());
		setScene(scene);
		show();

		owner.getScene().getRoot().setEffect(new BoxBlur());
		setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent windowEvent) {
				owner.getScene().getRoot().setEffect(null);
			}
		});
	}

	@Override
	public void close() {
		super.close();
		owner.getScene().getRoot().setEffect(null);
	}

}