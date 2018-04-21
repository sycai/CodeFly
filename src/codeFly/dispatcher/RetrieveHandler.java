package codeFly.dispatcher;

import codeFly.CodeFly;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

/**
 * Handling code retrieving from the repository
 */
public class RetrieveHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        String userName = HandlerTools.getUserName(exchange);

        String method = exchange.getRequestMethod();
        try {
            if (method.equalsIgnoreCase("GET")) {
                Map<String, String> queryPairs = HandlerTools.parseUriQuery(exchange.getRequestURI().getQuery());
                if (queryPairs.containsKey("verify")) {
                    JSONObject codeJson = new JSONObject();
                    boolean userIsActive = HandlerTools.isActiveUser(userName);
                    codeJson.put("userActive", userIsActive);

                    exchange.sendResponseHeaders(200, codeJson.toString().length());
                    exchange.getResponseBody().write(codeJson.toString().getBytes());
                    exchange.getResponseBody().close();
                } else {
                    int questionNumber = Integer.parseInt(queryPairs.get("qnum"));
                    // For now, just consider the language java
                    File userCode = CodeFly.repo.getUserCode(questionNumber, userName, "java");

                    // Return user code to the front-end
                    exchange.sendResponseHeaders(200, userCode.length());
                    OutputStream os = exchange.getResponseBody();
                    Files.copy(userCode.toPath(), os);
                    os.close();
                }

            } else {
                // Retrieve could only handle GET method. All other methods are invalid!
                HandlerTools.send404NotFound(exchange);
            }
        } catch (IOException ex) {
            CodeFly.logger.severe(ex.getCause().toString());
        }
    }
}
