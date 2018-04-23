package codeFly.dispatcher;

import codeFly.CodeFly;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

/**
 * Delete the current user from the active user set
 */
public class LogoutHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        try {
            String userName = HandlerTools.getUserName(exchange);
            // Delete user session from the server
            TaskDispatcher.activeUsers.remove(userName);
            exchange.sendResponseHeaders(200, 0);
            exchange.getResponseBody().close();
        } catch (IOException ex) {
            CodeFly.logger.severe(ex.getMessage());
        }
    }
}
