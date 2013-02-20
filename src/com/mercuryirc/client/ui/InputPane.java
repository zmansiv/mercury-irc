package com.mercuryirc.client.ui;

import com.mercuryirc.client.protocol.model.Channel;
import com.mercuryirc.client.protocol.model.Message;
import com.mercuryirc.client.protocol.model.Target;
import com.mercuryirc.client.ui.misc.FontAwesome;
import com.mercuryirc.client.ui.model.MessageRow;
import com.mercuryirc.client.ui.model.UserRow;
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
			String input = inputField.getText();
			if (input == null || input.isEmpty()) {
				return;
			}
			if (messageHistoryIndex == 9) {
				messageHistory.removeFirst();
			} else {
				messageHistoryIndex++;
			}
			messageHistory.add(input);
			Target target = appPane.getTabPane().getSelected().getTarget();
			if (input.startsWith("/")) {
				input = input.substring(1);
				final String[] tokens = input.split(" ");
				if (input.toLowerCase().startsWith("query") && tokens.length > 1) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							appPane.getTabPane().select(appPane.getTabPane().get(appPane.getConnection().resolveTarget(tokens[1])));
						}
					});
				} else if (input.toLowerCase().startsWith("msg")) {
					if (tokens.length > 2 && !tokens[1].startsWith("#")) {
						Message message = new Message(Message.Type.PRIVMSG, appPane.getConnection().getLocalUser().getName(), tokens[1], input.substring(input.indexOf(tokens[2])));
						appPane.getConnection().sendMessage(message);
						appPane.getTabPane().get(appPane.getConnection().resolveTarget(tokens[1])).getContentPane().getMessagePane().addRow(new MessageRow(appPane.getConnection(), message));
					}
				} else if (input.trim().equalsIgnoreCase("part") && target instanceof Channel) {
					appPane.getConnection().partChannel(target.getName());
				} else if (tokens[0].equalsIgnoreCase("topic") && !tokens[1].startsWith("#") && target instanceof Channel) {
					appPane.getConnection().writeLine("TOPIC " + target.getName() + " :" + input.substring(input.indexOf(tokens[1])));
				} else {
					appPane.getConnection().writeLine(input);
				}
			} else {
				Message message = new Message(Message.Type.PRIVMSG, appPane.getConnection().getLocalUser().getName(), target.getName(), input);
				appPane.getConnection().sendMessage(message);
				appPane.getTabPane().get(appPane.getConnection().resolveTarget(target.getName())).getContentPane().getMessagePane().addRow(new MessageRow(appPane.getConnection(), message));
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
						for (UserRow user : appPane.getContentPane().getUserPane().getUsers()) {
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