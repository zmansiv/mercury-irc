package com.mercuryirc.client.ui;

import com.mercuryirc.client.ui.model.Message;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MessagePanel extends VBox {

	private static final DateFormat TIME_FORMATTER = new SimpleDateFormat("hh:mm");
	private final List<Message> loadQueue = Collections.synchronizedList(new LinkedList<Message>());
	private final ObservableList<Message> messages = FXCollections.observableList(new LinkedList<Message>());
	private final WebView webView;
	private boolean loaded = false;

	public MessagePanel() {
		getStyleClass().add("message-panel");
		setId("right-side-panel");
		setMinWidth(350);
		webView = new WebView();
		VBox.setVgrow(webView, Priority.ALWAYS);
		final WebEngine webEngine = webView.getEngine();
		webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
			@Override
			public void handle(WebEvent<String> stringWebEvent) {
				System.out.println(stringWebEvent.getData());
			}
		});
		try {
			webEngine.load(new File("./res/html/MessageList.html").toURI().toURL().toExternalForm());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
			public void changed(ObservableValue observableValue, Worker.State state, Worker.State newState) {
				if (newState.equals(Worker.State.SUCCEEDED)) {
					onLoad();
				}
			}
		});
		HBox bottomBar = new HBox();
		bottomBar.getStyleClass().add("bottom-bar");
		bottomBar.setMinHeight(28);
		bottomBar.setMaxHeight(28);
		messages.addListener(new ListChangeListener<Message>() {

			@Override
			public void onChanged(ListChangeListener.Change<? extends Message> change) {
				while (change.next()) {
					if (change.wasAdded()) {
						for (Message message : change.getAddedSubList()) {
							if (loaded) {
								try {
								webView.getEngine().executeScript(String.format("addRow('%s', '%s', '%s', '%s')", message.getSource(), message.getContent(), "[" + TIME_FORMATTER.format(new Date()) + "]", message.getType().style()));
								} catch (Exception e) {
								}
							} else {
								loadQueue.add(message);
							}
						}
					}
				}
			}
		});
		getChildren().addAll(webView, bottomBar);
	}

	private void onLoad() {
		loaded = true;
		synchronized (loadQueue) {
			for (Message message : loadQueue) {
				webView.getEngine().executeScript(String.format("addRow('%s', '%s', '%s', '%s')", message.getSource(), message.getContent(), "[" + TIME_FORMATTER.format(new Date()) + "]", message.getType().style()));
			}
		}
		JSObject window = (JSObject) webView.getEngine().executeScript("window");
		window.setMember("panel", this);
	}

	public void openUrl(String url) {
		try {
			Desktop.getDesktop().browse(new URI(url));
		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}
	}

	public ObservableList<Message> messages() {
		return messages;
	}

}