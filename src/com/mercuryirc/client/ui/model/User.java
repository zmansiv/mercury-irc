package com.mercuryirc.client.ui.model;

import com.mercuryirc.client.protocol.model.RankComparator;

public class User implements Comparable<User> {

	private final String name;

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public int compareTo(User o) {
		return RankComparator.INSTANCE.compare(name, o.getName());
	}

}