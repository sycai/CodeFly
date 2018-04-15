package codeFly.dispatcher;

import codeFly.CodeFly;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Map;

/**
 * Handling code retrieving from the repository
 */
public class RetrieveHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        try {
            if (method.equalsIgnoreCase("GET")) {
                Map<String, String> queryPairs = HandlerTools.parseUriQuery(exchange.getRequestURI().getQuery());
                String userName = queryPairs.get("username");
                int questionNumber = Integer.parseInt(queryPairs.get("qnum"));
                // For now, just consider the language java
                File userCode = CodeFly.repo.getUserCode(questionNumber, userName, "java");

                // Return the user code in the repository
                exchange.sendResponseHeaders(200, userCode.length());
                OutputStream os = exchange.getResponseBody();
                Files.copy(userCode.toPath(), os);
                os.close();
            } else {
                // Retrieve could only handle GET method. All other methods are invalid!
                HandlerTools.send404NotFound(exchange);
            }
        } catch (IOException ex) {
            CodeFly.logger.severe(ex.getCause().toString());
        }
    }
}
