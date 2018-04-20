package codeFly.dispatcher;

import codeFly.CodeFly;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class AboutPageHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        try {
            String method = exchange.getRequestMethod();
            if (method.equalsIgnoreCase("GET")) {
                File aboutPage = new File(CodeFly.ROOT_DIR, "/frontEnd/about.html");
                exchange.sendResponseHeaders(200, aboutPage.length());
                OutputStream os = exchange.getResponseBody();
                Files.copy(aboutPage.toPath(), os);
                os.close();
                CodeFly.logger.info(String.format("Sent %s to client: %S",
                        aboutPage.getName(), exchange.getRemoteAddress()));
            } else {
                // Only support "GET" method
                HandlerTools.send404NotFound(exchange);
            }
        } catch (IOException ex) {
            CodeFly.logger.severe(ex.getMessage());
        }
    }
}
