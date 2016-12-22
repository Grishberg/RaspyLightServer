package com.grishberg.servlets;

import com.grishberg.services.accounts.AccountService;

/**
 * Created by grishberg on 21.12.16.
 */
public class LightServlet extends BaseHttpServlet {
    public LightServlet(AccountService accountService) {
        super(accountService);
    }
}
