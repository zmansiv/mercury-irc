package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import com.mercuryirc.client.ui.misc.FontAwesome;
import com.mercuryirc.model.Server;
import com.mercuryirc.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ConnectPane extends VBox {

	private final ConnectStage stage;

	private TextField netName = new TextField();
	private TextField netHost = new TextField();
	private TextField netPort = new TextField();
	private TextField netPass = new PasswordField();
	private CheckBox netSsl = new CheckBox();

	private TextField userNick = new TextField();
	private TextField userUser = new TextField();
	private TextField userReal = new TextField();
	private TextField userPass = new PasswordField();

	public ConnectPane(final ConnectStage stage) {
		this.stage = stage;

		getStyleClass().add("dark-pane");
		setId("container");

		VBox center = new VBox();
		center.setId("center");
		center.getChildren().addAll(
				createHeader("network", true),
				createField("Name", "Freenode", netName),
				createField("Host", "irc.freenode.net", netHost),
				createField("Port", "6667", netPort),
				createField("Password", "", netPass),
				createField("SSL", "", netSsl),

				createHeader("user", false),
				createField("Nickname", "MercuryUser", userNick),
				createField("Username", "mercury", userUser),
				createField("Real name", "mercury", userReal),
				createField("Password", "", userPass)
		);
		Button connectButton = new Button("connect");
		connectButton.getStyleClass().add("blue");
		connectButton.setId("connect-button");
		connectButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				Server server = new Server(netName.getText(), netHost.getText(), Integer.parseInt(netPort.getText()), netPass.getText().equals("") ? null : netPass.getText(), netSsl.isSelected());
				User user = new User(server, userNick.getText(), userUser.getText(), userReal.getText());
				if (!userPass.getText().equals("")) {
					user.setNickservPassword(userPass.getText());
				}
				Mercury.connect(server, user);
				stage.close();
			}
		});
		getChildren().addAll(center, connectButton);
	}

	private Node createHeader(String title, boolean first) {
		Label label = new Label(title);
		label.setId("header");
		if (first) {
			HBox box = new HBox();
			HBox.setHgrow(box, Priority.ALWAYS);
			Region spacer = new Region();
			HBox.setHgrow(spacer, Priority.ALWAYS);
			Button closeButton = FontAwesome.createIconButton(FontAwesome.REMOVE, "", false, null);
			closeButton.setId("close-button");
			closeButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					stage.close();
				}
			});
			box.getChildren().addAll(label, spacer, closeButton);
			return box;
		}
		return label;
	}

	private HBox createField(String label, String prompt, Control control) {
		if (control instanceof TextField) {
			((TextField) control).setPromptText(prompt);
		}

		HBox box = new HBox();
		Label labelObj = new Label(label);
		labelObj.setMinWidth(100);
		labelObj.setMaxWidth(100);
		control.setMinWidth(100);
		control.setMaxWidth(100);
		box.getChildren().addAll(labelObj, control);
		return box;
	}

}