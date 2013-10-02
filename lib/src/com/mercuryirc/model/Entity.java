package com.mercuryirc.model;

import javafx.beans.property.ReadOnlyStringProperty;

public interface Entity {

	public Server getServer();

	public String getName();

	public ReadOnlyStringProperty getNameProperty();

}