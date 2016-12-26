package com.grishberg.servlets;

import com.grishberg.common.JsonSerializer;
import com.grishberg.data.models.response.RestResponse;
import com.grishberg.services.accounts.AccountService;
import com.grishberg.services.light.LightService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by grishberg on 21.12.16.
 */
public class LightServlet extends BaseHttpServlet {
    public static final String PAGE_URL = "/light";
    private final LightService lightService;

    public LightServlet(final AccountService accountService, final LightService lightService) {
        super(accountService);
        this.lightService = lightService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (getUser(req) == null) {
            sendAuthError(resp);
            return;
        }
        resp.getWriter().println(JsonSerializer.toJson(
                new RestResponse<Boolean>().setData(lightService.checkState(0))));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (getUser(req) == null) {
            sendAuthError(resp);
            return;
        }
        long status = getLongParam(req, "status");
        lightService.changeState(0, status == 1);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(JsonSerializer.toJson(
                new RestResponse<Boolean>().setData(lightService.checkState(0))));
    }
}
