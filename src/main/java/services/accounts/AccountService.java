package services.accounts;

import data.models.PagingResult;
import data.models.User;
import data.exceptions.*;

/**
 * Created by grishberg on 21.12.16.
 */
public interface AccountService {
    void addNewUser(String login, String password, String name);

    User getUserByLogin(String login);

    User getUserBySessionId(String sessionId);

    void addSession(String sessionId, User user);

    void deleteSession(String sessionId);

    boolean checkAuth(String login, String password, String id);

    PagingResult<User> getUsers(int page) throws DbException;

    int getPageSize();

    User getUserByAccessToken(String accessToke);
}
