package com.grishberg.services.accounts;

import com.grishberg.data.models.PagingResult;
import com.grishberg.data.models.User;
import com.grishberg.data.exceptions.*;

/**
 * Created by grishberg on 21.12.16.
 */
public interface AccountService {
    void addNewUser(String login, String password, String name, User.UserRole role);

    User getUserByLogin(String login);

    String checkAuth(String login, CharSequence password);

    String updateToken(String login);

    PagingResult<User> getUsers(int page) throws DbException;

    int getPageSize();

    User getUserByAccessToken(String accessToke);
}
