package codeFly.dispatcher;

import codeFly.CodeFly;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * Responsible for sending front-end modules to the browser
 */
public class FrontendModuleHandler implements HttpHandler{
    private static final String FRONTEND_ROOT = CodeFly.ROOT_DIR + "/frontEnd";

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        File moduleFile = new File(FRONTEND_ROOT + exchange.getRequestURI());
        exchange.sendResponseHeaders(200, moduleFile.length());
        OutputStream os = exchange.getResponseBody();
        Files.copy(moduleFile.toPath(), os);
        os.close();
    }
}
