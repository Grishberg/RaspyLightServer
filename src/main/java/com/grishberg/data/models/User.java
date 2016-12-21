package com.grishberg.data.models;

import lombok.Data;

/**
 * Created by grishberg on 21.12.16.
 */
@Data
public class User {
    public enum UserRole {
        ADMIN,
        USER,
        GUEST,
    }

    private final String login;
    private final CharSequence password;
    private final String name;
    private final UserRole role;

    @Override
    public int hashCode() {
        return login.hashCode();
    }
}
