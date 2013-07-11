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
	public static final String PLUS = "\uf067";
	public static final String GLOBE = "\uf0ac";
	public static final String COG = "\uf013";
	public static final String COMMENTS = "\uf086";
	public static final String USER = "\uf007";
	public static final String PENCIL = "\uf040";
	public static final String SIGN_OUT = "\uf08b";
	public static final String INFO = "\uf05a";
	public static final String REPLY = "\uf112";

	public static Button createIconButton(String iconName) {
		return createIconButton(iconName, "", true, null);
	}

	public static Button createIconButton(String iconName, String styleClass) {
		return createIconButton(iconName, "", true, styleClass);
	}

	public static Button createIconButton(String iconName, boolean pad) {
		return createIconButton(iconName, "", pad, null);
	}

	public static Button createIconButton(String iconName, String text, String styleClass) {
		return createIconButton(iconName, text, true, styleClass);
	}

	public static Button createIconButton(String iconName, String text, boolean pad, String styleClass) {
		ButtonBuilder builder = ButtonBuilder.create()
				.text(text)
				.graphic(createIcon(iconName, styleClass != null))
				.contentDisplay(ContentDisplay.RIGHT)
				.focusTraversable(false);
		if (pad) {
			builder.minHeight(33).maxHeight(33);
			if (text.equals("")) {
				builder.minWidth(33).maxWidth(33);
			}
		}
		if (styleClass != null) {
			builder.styleClass(styleClass);
			if (pad) {
				builder.minHeight(32).maxHeight(32).style("-fx-padding: 0px 5px 0px 5px;");
			}
		}
		return builder.build();
	}

	public static Label createIconLabel(String iconName, String text) {
		return LabelBuilder.create()
				.text(text)
				.graphic(createIcon(iconName))
				.contentDisplay(ContentDisplay.LEFT)
				.build();
	}

	public static Label createIcon(String iconName) {
		return createIcon(iconName,  false);
	}

	public static Label createIcon(String iconName, boolean translate) {
		LabelBuilder builder = LabelBuilder.create()
				.text(iconName)
				.styleClass("icon");
		if (translate) {
			builder.translateY(3);
		}
		return builder.build();
	}

}