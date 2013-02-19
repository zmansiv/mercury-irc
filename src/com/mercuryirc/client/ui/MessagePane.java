package com.mercuryirc.client.ui;

import com.mercuryirc.client.Mercury;
import com.mercuryirc.client.ui.model.MessageRow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MessagePane extends VBox {

	private static final DateFormat TIME_FORMATTER = new SimpleDateFormat("hh:mmaa");

	private final WebView webView;
	private InputPane inputPane;
	private boolean pageLoaded;

	private final List<MessageRow> loadQueue;

	public MessagePane(ApplicationPane appPane) {
		webView = new WebView();
		inputPane = new InputPane(appPane);
		loadQueue = Collections.synchronizedList(new ArrayList<MessageRow>());

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

	public void addRow(MessageRow message) {
		if (pageLoaded) {
			webView.getEngine().executeScript(String.format("addRow('%s', '%s', '%s', '%s')", message.getSource(), message.getContent(), TIME_FORMATTER.format(new Date()), message.getType().style()));
		} else {
			loadQueue.add(message);
		}
	}

	private void onLoad() {
		pageLoaded = true;
		synchronized (loadQueue) {
			for (MessageRow message : loadQueue) {
				addRow(message);
			}
		}
		JSObject window = (JSObject) webView.getEngine().executeScript("window");
		window.setMember("panel", this);
	}

	private class LoadListener implements ChangeListener<Worker.State> {

		public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State state, Worker.State newState) {
			if (newState.equals(Worker.State.SUCCEEDED)) {
				onLoad();
			}
		}
	}

}