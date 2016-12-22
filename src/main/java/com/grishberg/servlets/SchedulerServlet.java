package com.grishberg.servlets;

import com.grishberg.data.models.User;
import com.grishberg.data.models.sheduler.ScheduleElement;
import com.grishberg.services.accounts.AccountService;
import com.grishberg.services.sheduler.SchedulerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by grishberg on 22.12.16.
 */
public class SchedulerServlet extends BaseHttpServlet {
    public static final String PAGE_URL = "/schedule";
    private final SchedulerService schedulerService;

    public SchedulerServlet(final AccountService accountService,
                            final SchedulerService schedulerService) {
        super(accountService);
        this.schedulerService = schedulerService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final User user = getUser(req);
        if (user == null) {
            sendAuthError(resp);
            return;
        }
        sendOkResponse(resp, schedulerService.getSchedules());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (getUser(req) == null) {
            sendAuthError(resp);
            return;
        }

        final String dow = req.getParameter("dow");
        final long dateTime = getLongParam(req, "dateTime");
        final boolean isEnabled = getLongParam(req, "enabled") == 1;

        schedulerService.addSchedule(new ScheduleElement(dow, dateTime, isEnabled));
        sendOkResponse(resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (getUser(req) == null) {
            sendAuthError(resp);
            return;
        }
        final long id = getLongParam(req, "id");

        final boolean result = schedulerService.removeSchedule(id);
        sendOkResponse(resp, result);
    }
}
