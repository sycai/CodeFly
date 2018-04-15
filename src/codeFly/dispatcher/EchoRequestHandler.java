package codeFly.dispatcher;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * A place holder for module that have not been implemented.
 */
public class EchoRequestHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Fetch request line and headers
        String response = exchange.getRequestMethod() +
                " " + exchange.getRequestURI() +
                " " + exchange.getProtocol() +
                "\n";
        for (String key : exchange.getRequestHeaders().keySet()) {
            response += key + ": " + String.join(", ",exchange.getRequestHeaders().get(key)) + "\n";
        }

        // Fetch request body
        StringBuilder reqBody = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        String line;
        while ((line = reader.readLine()) != null) {
            reqBody.append(line);
        }
        response += "\n" + reqBody;

        // Echo back
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
