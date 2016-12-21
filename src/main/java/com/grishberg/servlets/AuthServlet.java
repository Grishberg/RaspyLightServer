package com.grishberg.servlets;

import com.grishberg.common.JsonSerializer;
import com.grishberg.data.exceptions.Errors;
import com.grishberg.data.models.response.RestResponse;
import com.grishberg.services.accounts.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by grishberg on 21.12.16.
 */
public class AuthServlet extends HttpServlet {
    public static final String PAGE_URL = "/auth";
    public static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    private final AccountService accountService;

    public AuthServlet(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        resp.setContentType(CONTENT_TYPE);

        if (login == null || password == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(JsonSerializer.toJson(new RestResponse().setError(Errors.AUTH_EMPTY_FIELDS_ERROR)));
            return;
        }

        final String token = accountService.checkAuth(login, password);
        if (token != null) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(JsonSerializer.toJson(
                    new RestResponse<String>().setData(token)));
            return;
        }

        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.getWriter().println(JsonSerializer.toJson(
                new RestResponse().setError(Errors.AUTH_ERROR)));
    }
}
