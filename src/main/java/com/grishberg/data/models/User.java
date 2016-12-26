package com.grishberg.data.models;

import lombok.Getter;

/**
 * Created by grishberg on 21.12.16.
 */
public class User {
    public enum UserRole {
        ADMIN,
        USER,
        GUEST,
    }

    @Getter
    private String login;
    @Getter
    private String password;
    @Getter
    private String name;
    private UserRole roleAsObject;
    private String role;

    public User() {
    }

    public User(String login, String password, String name, UserRole roleAsObject) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.roleAsObject = roleAsObject;
    }

    public UserRole getRole() {
        if (roleAsObject == null) {
            try {
                return UserRole.valueOf(role);
            } catch (Exception e) {
                return UserRole.GUEST;
            }
        }
        return roleAsObject;
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }
}
