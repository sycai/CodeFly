package codeFly.dispatcher;

import codeFly.CodeFly;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class AboutPageHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        try {

            if (method.equalsIgnoreCase("GET")) {
                //get query contained in URI
                String query = exchange.getRequestURI().getQuery();
                if (query == null) {
                    File aboutPage = new File(CodeFly.ROOT_DIR + "frontEnd/about.html");
                    exchange.sendResponseHeaders(200, aboutPage.length());
                    OutputStream os = exchange.getResponseBody();
                    Files.copy(aboutPage.toPath(), os);
                    os.close();
                    CodeFly.logger.info(String.format("Sending %s to client: %S",
                            aboutPage.getName(), exchange.getRemoteAddress()));
                } else {
                    String userName = HandlerTools.getUserName(exchange);
                    // Check whether this is a logged in account
                    if (!HandlerTools.isActiveUser(userName)) {
                        userName = null;
                    }
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("userName", userName);
                    //convert JSONObject to String
                    String jsonStr = jsonObj.toString();
                    // response with success
                    exchange.sendResponseHeaders(200, jsonStr.length());
                    exchange.getResponseBody().write(jsonStr.getBytes());
                    exchange.close();
                }
            } else {
                // Only support "GET" method
                HandlerTools.send404NotFound(exchange);
            }
        } catch (IOException ex) {
            CodeFly.logger.severe(ex.getMessage());
        }
    }
}

