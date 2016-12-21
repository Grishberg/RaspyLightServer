package com.grishberg.data.models;

import lombok.Getter;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by grishberg on 21.12.16.
 */
@Getter
public class UserToken {
    private long time;
    private UUID token;

    public UserToken(final UUID token, final long time) {
        this.token = token;
        this.time = time;
    }

    public UserToken(final long tokenDuration) {
        time = Calendar.getInstance().getTimeInMillis() + tokenDuration;
        token = UUID.randomUUID();
    }

    public boolean checkToken(final String token) {
        return this.token.toString().equals(token);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof UserToken)) {
            return false;
        }
        UserToken userToken = (UserToken) obj;
        if (!this.token.equals(userToken.token)) {
            return false;
        }
        return time > userToken.time;
    }
}
