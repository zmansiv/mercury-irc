package com.mercuryirc.network.numerics;

import com.mercuryirc.model.User;
import com.mercuryirc.network.Connection;

public class Who implements Connection.NumericHandler {
	public boolean applies(Connection connection, int numeric) {
		return numeric == 352 || numeric == 315;
	}

	public void process(Connection connection, String line, String[] parts) {
		if(Integer.parseInt(parts[1]) == 352) {
			String userName = parts[4];
			String host = parts[5];
			String nick = parts[7];
			String realName = line.substring(line.indexOf(':', 1) + 3);

			User user = connection.getServer().getUser(nick);
			user.setHost(host);
			user.setUserName(userName);
			user.setRealName(realName);
		} else {
			// End of /WHO list

		}
	}
}
