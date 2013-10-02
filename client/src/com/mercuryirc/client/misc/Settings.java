package com.mercuryirc.client.misc;

import com.mercuryirc.client.Mercury;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {

	private static final File APP_DIR;
	private static final Properties properties;

	static {
		APP_DIR = new File(System.getProperty("user.home"), ".mercury");
		properties = new Properties();
		try {
			File file = new File(APP_DIR, "settings.ini");
			if (file.exists()) {
				FileInputStream fileInput = new FileInputStream(file);
				properties.load(fileInput);
				fileInput.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void init() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				Stage stage = Mercury.getStage();
				set("bounds", String.format("%s %s %s %s", stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight()));
				Mercury.saveConnections();
				save();
			}
		}));
	}

	public static String get(String key) {
		return properties.getProperty(key);
	}

	public static void set(String key, String value) {
		properties.setProperty(key, value);
	}

	public static void save() {
		try {
			File file = new File(APP_DIR, "settings.ini");
			if (!APP_DIR.exists()) {
				APP_DIR.mkdir();
			}
			FileOutputStream fileOutput = new FileOutputStream(file);
			properties.store(fileOutput, "Mercury");
			fileOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}