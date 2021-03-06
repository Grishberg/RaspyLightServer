package com.grishberg.main;

import com.grishberg.common.ConfigContext;
import com.grishberg.common.ConfigReader;
import com.grishberg.data.models.User;
import com.grishberg.services.accounts.AccountService;
import com.grishberg.services.accounts.AccountServiceImpl;
import com.grishberg.services.light.LightService;
import com.grishberg.services.light.LightServiceImpl;
import com.grishberg.services.sheduler.FileScheduleStorageImpl;
import com.grishberg.services.sheduler.ScheduleServiceImpl;
import com.grishberg.services.sheduler.SchedulerService;
import com.grishberg.servlets.AuthServlet;
import com.grishberg.servlets.LightServlet;
import com.grishberg.servlets.SchedulerServlet;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import org.apache.logging.log4j.LogManager;
import org.eclipse.jetty.server.session.DefaultSessionIdManager;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by grishberg on 20.12.16.
 */
public class Main {
    static org.apache.logging.log4j.Logger log = null;
    public static final int PAGE_SIZE = 30;

    public static void main(String[] args) throws Exception {
        System.setProperty("log4j.configurationFile", "cfg/log4j2.xml");
        log = LogManager.getLogger(Main.class.getName());

        ConfigContext configContext = ConfigReader.readConfig();
        AccountService accountService = new AccountServiceImpl();
        for (User user : configContext.getUsers()) {
            accountService.addNewUser(user.getLogin(), user.getPassword(), user.getName(), user.getRole());
        }

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        //Configuration cfg = initTemplater(context);
        //configContext.setTemplateConfiguration(cfg);

        LightService lightService = new LightServiceImpl(configContext);

        SchedulerService schedulerService = new ScheduleServiceImpl(new FileScheduleStorageImpl());

        AuthServlet authServlet = new AuthServlet(accountService);
        context.addServlet(new ServletHolder(authServlet), AuthServlet.PAGE_URL);

        SchedulerServlet schedulerServlet = new SchedulerServlet(accountService, schedulerService);
        context.addServlet(new ServletHolder(schedulerServlet), SchedulerServlet.PAGE_URL);

        LightServlet lightServlet = new LightServlet(accountService, lightService);
        context.addServlet(new ServletHolder(lightServlet), LightServlet.PAGE_URL);


/*
        ServletHolder fileUploadServletHolder = new ServletHolder(addProjectServlet);
        fileUploadServletHolder.getRegistration().setMultipartConfig(new MultipartConfigElement("com/grishberg/data/tmp"));
        context.addServlet(fileUploadServletHolder, AddProjectServlet.PAGE_URL);

        ServletHolder testDataUploadServletHolder = new ServletHolder(addTestDataServlet);
        testDataUploadServletHolder.getRegistration().setMultipartConfig(new MultipartConfigElement("com/grishberg/data/tmp"));
        context.addServlet(testDataUploadServletHolder, AddTestDataServlet.PAGE_URL);
*/
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("public_html");
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});

        Server server = new Server(configContext.getPort());
        final DefaultSessionIdManager idmgr = new DefaultSessionIdManager(server);
        idmgr.setWorkerName("worker_1");
        server.setSessionIdManager(idmgr);

        server.setHandler(handlers);
        server.start();
        log.info("--------------");
        log.info("Smart home server v.2.0.2");
        log.info(String.format("Starting at http://127.0.0.1:%d", configContext.getPort()));
        log.info("--------------");
        try {
            server.join();
        } finally {
            log.info("shutdown");
            lightService.stop();
        }
    }

    /**
     * Инициализация шаблонизатора
     *
     * @param context
     * @return
     */
    private static Configuration initTemplater(ServletContextHandler context) throws IOException {
        //Инициализация FreeMaker
        // 1. Configure FreeMarker
        //
        // You should do this ONLY ONCE, when your application starts,
        // then reuse the same Configuration object elsewhere.

        Configuration cfg = new Configuration();
        FileTemplateLoader ftl1 = new FileTemplateLoader(new File("templates/html"));
        MultiTemplateLoader mtl = new MultiTemplateLoader(new TemplateLoader[]{ftl1});
        cfg.setTemplateLoader(mtl);
        // Some other recommended settings:
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return cfg;
    }
}
