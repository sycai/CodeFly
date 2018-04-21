package codeFly.dispatcher;

import codeFly.CodeFly;
import codeFly.tester.JavaTestEngine;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashSet;

public class TaskDispatcher {
    /**
     * The only instance of TaskDispatcher
     */
    private static TaskDispatcher dispatcher = null;

    /**
     * Class constants
     */
    private static final int CONNECTION_BACKLOG_SIZE = 50;
    private static final String[] FRONT_END_MODULES = { "/css", "/images", "/js" };
    public static final String COOKIE_KEY = "username=";

    /**
     * Class variables
     */
    private final int port;
    public static final HashSet<String> activeUsers = new HashSet<>();

    private TaskDispatcher(int port) {
        this.port = port;
    }

    /**
     * Factory method for fetching the TaskDispatcher
     * 
     * @param port
     *            the port number to be listened to
     * @return the dispatcher
     */
    public static TaskDispatcher getTaskDispatcher(int port) {
        if (dispatcher == null) {
            dispatcher = new TaskDispatcher(port);
        }
        return dispatcher;
    }

    /**
     * Start a HTTP listener that binds to the wildcard address
     */
    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), CONNECTION_BACKLOG_SIZE);
            server.createContext("/", new WelcomePageHandler());
            for (String module : FRONT_END_MODULES) {
                server.createContext(module, new FrontendModuleHandler());
            }
            server.createContext("/register", new RegisterHandler());
            server.createContext("/login", new LoginHandler());
            server.createContext("/questions", new QuestionsHandler());
            server.createContext("/editor", new EditorPageHandler());
            server.createContext("/retrieve", new RetrieveHandler());
            server.createContext("/about", new AboutPageHandler());

            // Default executor
            server.setExecutor(null);
            server.start();

            // FIXME: delete this invocation after the testing module is sufficiently tested
            // testTester();
        } catch (IOException e) {
            CodeFly.logger.info("HTTP listener error: " + e.getMessage());
        }
    }

    // FIXME: delete this function after the testing module is sufficiently tested.
    public void testTester() {
        try {
            System.out.println(JavaTestEngine.getTestResult(1, "Bob"));
            System.out.println(JavaTestEngine.getTestResult(1, "Amy"));
            System.out.println(JavaTestEngine.getTestResult(2, "Bob"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
