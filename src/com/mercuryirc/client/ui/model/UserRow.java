package com.mercuryirc.client.ui.model;

import com.mercuryirc.client.protocol.misc.IrcUtils;
import com.mercuryirc.client.protocol.model.RankComparator;

public class UserRow implements Comparable<UserRow> {

	private String name;

	public UserRow(String name) {
		this.name = name;
	}

	public String getName() {
		if (IrcUtils.isRank(name.charAt(0)))
			return name.substring(1);
		else
			return name;
	}

	@Override
	public String toString() {
		return name;
	}

	public int compareTo(UserRow o) {
		return RankComparator.INSTANCE.compare(name, o.name);
	}

}