package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import com.mercuryirc.client.ui.model.Message;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class MessageList extends VBox {
	private static final DateFormat TIME_FORMATTER = new SimpleDateFormat("hh:mm");

	private final WebView webView;
	private InputPane inputPane;
	private boolean pageLoaded;

	private final ObservableList<Message> messages;
	private final List<Message> loadQueue;

	public MessageList() {
		webView = new WebView();
		inputPane = new InputPane();
		messages = FXCollections.observableArrayList(new ArrayList<Message>());
		loadQueue = Collections.synchronizedList(new ArrayList<Message>());

		webView.setContextMenuEnabled(false);
		VBox.setVgrow(webView, Priority.ALWAYS);

		WebEngine engine = webView.getEngine();
		engine.getLoadWorker().stateProperty().addListener(new LoadListener());
		engine.load(Mercury.class.getResource("./res/html/MessageList.html").toExternalForm());

		getChildren().addAll(webView, inputPane);
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

	private void addRow(Message message) {
		webView.getEngine().executeScript(String.format("addRow('%s', '%s', '%s', '%s')", message.getSource(), message.getContent(), "[" + TIME_FORMATTER.format(new Date()) + "]", message.getType().style()));
	}

	private void onLoad() {
		pageLoaded = true;
		synchronized (loadQueue) {
			for(Message message : loadQueue) {
				addRow(message);
			}
		}
		JSObject window = (JSObject) webView.getEngine().executeScript("window");
		window.setMember("panel", this);
	}

	class MessageChangeListener implements ListChangeListener<Message> {
		public void onChanged(Change<? extends Message> change) {
			while(change.next()) {
				if(change.wasAdded()) {
					for(Message message : change.getAddedSubList()) {
						if(pageLoaded)
							addRow(message);
						else
							loadQueue.add(message);
					}
				}
			}
		}
	}

	class LoadListener implements ChangeListener<Worker.State> {
		public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State state, Worker.State newState) {
			if (newState.equals(Worker.State.SUCCEEDED)) {
				onLoad();
			}
		}
	}
}
