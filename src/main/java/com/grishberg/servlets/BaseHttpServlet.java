package com.grishberg.servlets;

import com.grishberg.common.JsonSerializer;
import com.grishberg.data.exceptions.Errors;
import com.grishberg.data.models.User;
import com.grishberg.data.models.response.RestResponse;
import com.grishberg.services.accounts.AccountService;
import org.apache.logging.log4j.LogManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by grishberg on 21.12.16.
 */
public abstract class BaseHttpServlet extends HttpServlet {
    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(BaseHttpServlet.class.getName());
    private final AccountService accountService;
    public static final String ACCESS_TOKEN = "accessToken";

    public BaseHttpServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Вернуть пользователя по сессии или токену
     *
     * @param req
     * @return
     */
    protected User getUser(HttpServletRequest req) {
        String accessToke = req.getParameter(ACCESS_TOKEN);
        if (accessToke != null) {
            User user = accountService.getUserByAccessToken(accessToke);
            if (user != null) {
                return user;
            }
        }
        return null;
    }

    protected void sendAuthError(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().println(JsonSerializer.toJson(new RestResponse().setError(Errors.NOT_AUTHORIZED)));
    }

    protected void sendOkResponse(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(JsonSerializer.toJson(new RestResponse<Boolean>().setData(true)));
    }

    protected <T> void sendOkResponse(HttpServletResponse resp, final T data) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(JsonSerializer.toJson(new RestResponse<T>().setData(data)));
    }

    /**
     * Извлечь номер страницы
     *
     * @param req
     * @return
     */
    protected int getPage(HttpServletRequest req) {
        String pageStr = req.getParameter("page");
        if (pageStr == null || pageStr.isEmpty()) {
            pageStr = "1";
        }
        try {
            return Integer.valueOf(pageStr);
        } catch (NumberFormatException e) {
            logger.info(e.getMessage());
        }
        return 1;
    }

    protected long getLongParam(HttpServletRequest req, String name) {
        String val = req.getParameter(name);
        if (val == null || val.isEmpty()) {
            val = "0";
        }
        try {
            return Long.valueOf(val);
        } catch (NumberFormatException e) {
            logger.info(e.getMessage());
        }
        return 0;
    }
}
