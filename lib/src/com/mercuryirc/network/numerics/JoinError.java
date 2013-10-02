package com.mercuryirc.network.numerics;

import com.mercuryirc.model.Channel;
import com.mercuryirc.network.Connection;

/**
 * An error that is raised when joining a channel fails.
 * Reasons a channel join can fail include: keywords, invite-only, bans.
 * @author rvbiljouw
 */
public class JoinError implements Connection.NumericHandler {
    @Override
    public boolean applies(Connection connection, int numeric) {
        return numeric == 473;
    }

    @Override
    public void process(Connection connection, String line, String[] parts) {
        String reason = formulateReason(parts);
        String channelName = parts[3];

        Channel channel = new Channel(connection.getServer(), channelName);
        connection.getCallback().onJoinError(connection, channel, reason);
    }

    private String formulateReason(String[] parts) {
        StringBuilder reasonBuilder = new StringBuilder();
        for(int index = 4; index < parts.length; index++) {
            reasonBuilder.append(parts[index]);
            if(index + 1 < parts.length) {
                reasonBuilder.append(' ');
            }
        }
        return reasonBuilder.toString().substring(1);
    }
}
