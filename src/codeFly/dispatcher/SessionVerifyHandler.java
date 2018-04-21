package codeFly.dispatcher;

import codeFly.CodeFly;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Verifies the session cookie of http request, and sends back a JSON object indicating
 * whether tis session has expired or not
 */
public class SessionVerifyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        try {
            String userName = HandlerTools.getUserName(exchange);
            JSONObject object = new JSONObject();
            object.put("userActive", HandlerTools.isActiveUser(userName));
            exchange.sendResponseHeaders(200, object.toString().length());
            OutputStream os = exchange.getResponseBody();
            os.write(object.toString().getBytes());
            os.close();
        } catch (IOException ex) {
            CodeFly.logger.severe(ex.getMessage());
        }
    }
}
