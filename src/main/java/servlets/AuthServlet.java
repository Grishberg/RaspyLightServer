package servlets;

import services.accounts.AccountService;

/**
 * Created by grishberg on 21.12.16.
 */
public class AuthServlet {
    public static final String PAGE_URL = "/auth";
    private final AccountService accountService;

    public AuthServlet(AccountService accountService) {
        this.accountService = accountService;
    }
}
