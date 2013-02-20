package com.mercuryirc.client.ui;

import com.mercuryirc.client.protocol.model.User;
import com.mercuryirc.client.ui.misc.FontAwesome;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.LinkedList;

public class InputPane extends HBox {

	private final ApplicationPane appPane;
	private final Label nickLabel;
	private final TextField inputField;
	private LinkedList<String> messageHistory = new LinkedList<>();
	private int messageHistoryIndex = -1;

	public InputPane(final ApplicationPane appPane) {
		this.appPane = appPane;
		getStyleClass().add("dark-pane");
		setId("input-pane");
		setAlignment(Pos.CENTER);
		setMinHeight(65);
		setMaxHeight(65);
		setSpacing(0);
		setPadding(new Insets(10, 5, 10, 5));
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		nickLabel = new Label("nick");
		inputField = new TextField();
		inputField.setOnAction(new InputHandler());
		inputField.setOnKeyPressed(new KeyHandler());
		Button sendButton = FontAwesome.createIconButton(FontAwesome.REPLY, "send", "blue");
		getChildren().addAll(nickLabel, inputField, spacer, sendButton);
	}

	public Label getNickLabel() {
		return nickLabel;
	}

	private class InputHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent actionEvent) {
			final String input = inputField.getText();
			if (input == null || input.isEmpty()) {
				return;
			}
			if (messageHistoryIndex == 9) {
				messageHistory.removeFirst();
			} else {
				messageHistoryIndex++;
			}
			messageHistory.add(input);
			if (input.toLowerCase().startsWith("/query ")) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						appPane.getTabPane().select(appPane.getTabPane().get(appPane.getConnection().resolveTarget(input.substring(7))));
					}
				});
			} else {
				appPane.getConnection().process(appPane.getTabPane().getSelected().getTarget(), input);
			}
			inputField.setText("");
		}

	}

	private class KeyHandler implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent keyEvent) {
			switch (keyEvent.getCode()) {
				case UP:
					if (messageHistoryIndex > -1) {
						inputField.setText(messageHistory.get(messageHistoryIndex > 0 && messageHistoryIndex < messageHistory.size() ? --messageHistoryIndex : messageHistoryIndex));
					}
					keyEvent.consume();
					break;
				case DOWN:
					if (messageHistoryIndex < 10) {
						inputField.setText(messageHistory.get(messageHistoryIndex < 9 && messageHistoryIndex > -1 ? ++messageHistoryIndex : messageHistoryIndex));
					}
					keyEvent.consume();
					break;
				case TAB:
					if (keyEvent.isControlDown()) {
						return;
					}
					String text = inputField.getText();
					int start = -1;
					for (int i = inputField.getCaretPosition() - 1; i >= 0; i--) {
						if (text.charAt(i) == ' ') {
							break;
						}
						start = i;
					}
					if (start != -1) {
						String beginning = text.substring(start, inputField.getCaretPosition()).toLowerCase();
						for (User user : appPane.getContentPane().getUserPane().getUsers()) {
							final String nick = user.getName();
							if (nick.toLowerCase().startsWith(beginning)) {
								inputField.deleteText(start, inputField.getCaretPosition());
								inputField.insertText(start, nick + " ");
								final int caret = start + nick.length() + 1;
								inputField.positionCaret(caret);
								setFocused(true);
								break;
							}
						}
					}
					keyEvent.consume();
					break;
			}
		}

	}

}