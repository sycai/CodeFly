package codeFly.dispatcher;

import codeFly.CodeFly;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

/**
 * Send welcome page to the browser (usually the index.html)
 */
public class WelcomePageHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String uriStr = exchange.getRequestURI().toString();
        // uri should be of the form "/" or "{address}:{port}/"
        if (!uriStr.matches(".*(:[0-9]*)?/$")) {
            String resNotFound = "404 not found";
            exchange.sendResponseHeaders(404, resNotFound.length());
            OutputStream os = exchange.getResponseBody();
            os.write(resNotFound.getBytes());
            os.close();
        } else {
            // FIXME: create index.html for use
            File welcomePage = new File(CodeFly.ROOT_DIR + "frontEnd/index.html");
            // response with a success response
            exchange.sendResponseHeaders(200, welcomePage.length());
            OutputStream os = exchange.getResponseBody();
            Files.copy(welcomePage.toPath(), os);
            os.close();
            CodeFly.logger.info(String.format("Sending %s to client: %S",
                    welcomePage.getName(), exchange.getRemoteAddress()));
        }
    }
}
