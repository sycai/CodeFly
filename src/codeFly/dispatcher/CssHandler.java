package codeFly.dispatcher;

import codeFly.CodeFly;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class CssHandler implements HttpHandler{
    private static final String FRONTEND_ROOT = CodeFly.ROOT_DIR + "/frontEnd";
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        File cssFile = new File(FRONTEND_ROOT + exchange.getRequestURI());
        System.out.println(cssFile.getPath());
        exchange.sendResponseHeaders(200, cssFile.length());
        OutputStream os = exchange.getResponseBody();
        Files.copy(cssFile.toPath(), os);
        os.close();
    }
}
