package com.grishberg.servlets;

import com.grishberg.services.accounts.AccountService;

import javax.servlet.http.HttpServlet;

/**
 * Created by grishberg on 21.12.16.
 */
public abstract class BaseHttpServlet extends HttpServlet {
    private final AccountService accountService;

    public BaseHttpServlet(AccountService accountService) {
        this.accountService = accountService;
    }
}
