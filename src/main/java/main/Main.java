package main;

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

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        SessionManager sm = new HashSessionManager();
        HashSessionIdManager idManager = new HashSessionIdManager();
        sm.setMaxInactiveInterval(60 * 24);
        sm.getSessionCookieConfig().setName("JSESSIONID_" + String.valueOf(configContext.getPort()));
        sm.setSessionIdManager(idManager);
        SessionHandler sessionHandler = new SessionHandler(sm);
        context.setSessionHandler(sessionHandler);

        Configuration cfg = initTemplater(context);
        configContext.setCfg(cfg);

        DbService dbService = new DbServiceImpl(PAGE_SIZE, null);
        AccountService accountService = new AccountServiceImpl(dbService);
        accountService.addNewUser("admin", "qwe123", "Администратор");

        ClassifierSender classifierSender = new ClassifierSenderRestImpl(configContext);
        JobManagerImpl jobManager = new JobManagerImpl(configContext, dbService, classifierSender);
        classifierSender.setListener(jobManager);

        AuthServlet authServlet = new AuthServlet(accountService);
        context.addServlet(new ServletHolder(authServlet), AuthServlet.PAGE_URL);

        CategoricalFeaturesServlet categoricalFeaturesServlet = new CategoricalFeaturesServlet(configContext, accountService, dbService);
        context.addServlet(new ServletHolder(categoricalFeaturesServlet), CategoricalFeaturesServlet.PAGE_URL);

        RegistrationServlet registrationServlet = new RegistrationServlet(accountService);
        context.addServlet(new ServletHolder(registrationServlet), RegistrationServlet.PAGE_URL);

        DetailProjectServlet detailProjectServlet = new DetailProjectServlet(configContext, accountService, dbService);
        context.addServlet(new ServletHolder(detailProjectServlet), DetailProjectServlet.PAGE_URL);

        ProjectsServlet projectsServlet = new ProjectsServlet(configContext, accountService, dbService);
        context.addServlet(new ServletHolder(projectsServlet), ProjectsServlet.PAGE_URL);

        SupposesServlet supposesServlet = new SupposesServlet(configContext, accountService, dbService);
        context.addServlet(new ServletHolder(supposesServlet), SupposesServlet.PAGE_URL);

        AddSupposeServlet addSupposeServlet = new AddSupposeServlet(configContext,
                accountService,
                dbService,
                jobManager);
        context.addServlet(new ServletHolder(addSupposeServlet), AddSupposeServlet.PAGE_URL);

        DetailSupposeServlet detailSupposeServlet = new DetailSupposeServlet(configContext,
                accountService,
                dbService,
                jobManager);
        context.addServlet(new ServletHolder(detailSupposeServlet), DetailSupposeServlet.PAGE_URL);

        AddProjectServlet addProjectServlet = new AddProjectServlet(configContext,
                accountService,
                dbService,
                jobManager);

        AddTestDataServlet addTestDataServlet = new AddTestDataServlet(configContext,
                accountService,
                dbService,
                jobManager);

        ServletHolder fileUploadServletHolder = new ServletHolder(addProjectServlet);
        fileUploadServletHolder.getRegistration().setMultipartConfig(new MultipartConfigElement("data/tmp"));
        context.addServlet(fileUploadServletHolder, AddProjectServlet.PAGE_URL);

        ServletHolder testDataUploadServletHolder = new ServletHolder(addTestDataServlet);
        testDataUploadServletHolder.getRegistration().setMultipartConfig(new MultipartConfigElement("data/tmp"));
        context.addServlet(testDataUploadServletHolder, AddTestDataServlet.PAGE_URL);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("public_html");
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});

        Server server = new Server(configContext.getPort());
        server.setHandler(handlers);
        server.start();
        log.info("--------------");
        log.info("saas server v.1.0.2");
        log.info(String.format("Starting at http://127.0.0.1:%d", configContext.getPort()));
        log.info("--------------");
        server.join();
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
