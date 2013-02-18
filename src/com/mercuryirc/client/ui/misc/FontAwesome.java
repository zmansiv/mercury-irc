package com.mercuryirc.client.ui.misc;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;

//based off of AwesomeDude class by Jens Deters
public class FontAwesome {

	public static final String MINUS = "\uf068";
	public static final String RESIZE_FULL = "\uf065";
	public static final String RESIZE_SMALL = "\uf066";
	public static final String REMOVE = "\uf00d";
	public static final String PLUS = "\uf0d5";
	public static final String GLOBE = "\uf0ac";
	public static final String COG = "\uf013";
	public static final String COMMENTS = "\uf086";
	public static final String USER = "\uf007";
	public static final String PENCIL = "\uf040";
	public static final String SIGN_OUT = "\uf08b";
	public static final String INFO = "\uf05a";
	public static final String ENVELOPE = "\uf0e0";
	public static final String LEGAL = "\uf0e3";

	public static Button createIconButton(String iconName) {
		return createIconButton(iconName, "");
	}

	public static Button createIconButton(String iconName, String text) {
		return ButtonBuilder.create()
				.text(text)
				.graphic(createIcon(iconName))
				.contentDisplay(ContentDisplay.LEFT)
				.build();
	}

	public static Label createIconLabel(String iconName, String text) {
		return LabelBuilder.create()
				.text(text)
				.graphic(createIcon(iconName))
				.contentDisplay(ContentDisplay.RIGHT)
				.build();
	}

	public static Label createIcon(String iconName) {
		return LabelBuilder.create()
				.text(iconName)
				.styleClass("icon")
				.build();
	}

}