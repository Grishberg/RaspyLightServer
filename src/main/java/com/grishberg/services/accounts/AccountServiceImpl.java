package com.grishberg.services.accounts;

import com.grishberg.data.exceptions.DbException;
import com.grishberg.data.models.PagingResult;
import com.grishberg.data.models.User;
import com.grishberg.data.models.UserToken;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by grishberg on 21.12.16.
 */
public class AccountServiceImpl implements AccountService {
    public static final int TOKEN_DURATION = 3600 * 1000;
    private final Map<User, UserToken> users;

    public AccountServiceImpl() {
        users = new ConcurrentHashMap<>();
    }

    @Override
    public void addNewUser(String login, String password, String name, User.UserRole role) {
        final User user = new User(login, password, name, role);
        final UserToken userToken = new UserToken(TOKEN_DURATION);
        users.put(user, userToken);
    }

    @Override
    public User getUserByLogin(String login) {
        for (Map.Entry<User, UserToken> entrySet : users.entrySet()) {
            final User user = entrySet.getKey();
            if (login.equals(user.getLogin())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public String updateToken(String login) {
        final User user = getUserByLogin(login);
        if (user == null) {
            return null;
        }
        UserToken token = new UserToken(TOKEN_DURATION);
        users.put(user, token);
        return token.getToken().toString();
    }

    @Override
    public String checkAuth(String login, CharSequence password) {
        final User user = getUserByLogin(login);
        if (user == null) {
            return null;
        }
        return user.getPassword().equals(password) ?
                users.get(user).getToken().toString() : null;
    }

    @Override
    public PagingResult<User> getUsers(int page) throws DbException {
        ArrayList<User> usersArray = new ArrayList<>();
        for (Map.Entry<User, UserToken> entrySet : users.entrySet()) {
            final User user = entrySet.getKey();
            usersArray.add(user);
        }
        return new PagingResult<>(page, page - 1, page + 1, page + 1, usersArray);
    }

    @Override
    public int getPageSize() {
        return 10;
    }

    @Override
    public User getUserByAccessToken(String accessToke) {
        for (Map.Entry<User, UserToken> entrySet : users.entrySet()) {
            final User user = entrySet.getKey();
            final UserToken userToken = entrySet.getValue();
            if (userToken.checkToken(accessToke)) {
                return user;
            }
        }
        return null;
    }
}
