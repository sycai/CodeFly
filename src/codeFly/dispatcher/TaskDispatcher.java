package codeFly.dispatcher;

import codeFly.CodeFly;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class TaskDispatcher {
    /**
     * The only instance of TaskDispatcher
     */
    private static TaskDispatcher dispatcher = null;

    /**
     * Class constants
     */
    private static final int CONNECTION_BACKLOG_SIZE = 50;
    private static final String[] FRONT_END_MODULES = {"/css", "/images", "/js"};

    /**
     * Class variables
     */
    private final int port;

    private TaskDispatcher(int port) {
        this.port = port;
    }

    /**
     * Factory method for fetching the TaskDispatcher
     * @param port the port number to be listened to
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
            server.createContext("/register", new EchoRequestHandler());
            server.createContext("/login", new EchoRequestHandler());
            server.createContext("/questions", new EchoRequestHandler());
            server.createContext("/editor", new EditorPageHandler());
            server.createContext("/retrieve", new EchoRequestHandler());
            // Default executor
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            CodeFly.logger.info("HTTP listener error: " + e.getMessage());
        }
    }

}
