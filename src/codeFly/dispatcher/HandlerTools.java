package codeFly.dispatcher;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * A bundle of helper functions to handle the most common tasks of all handlers
 */
public class HandlerTools {
    private HandlerTools() {} // Not instantiatable

    /**
     * Send 404 not found to the browser. The output stream is then closed
     * @param exchange
     * @throws IOException
     */
    public static void send404NotFound(HttpExchange exchange) throws IOException {
        String resNotFound = "404 not found";
        exchange.sendResponseHeaders(200, resNotFound.length());
        OutputStream os = exchange.getResponseBody();
        os.write(resNotFound.getBytes());
        os.close();
    }

    /**
     * Parse query keys and values into a map.
     * @param query
     * @return
     */
    public static Map<String, String> parseUriQuery(String query) {
        HashMap res = new HashMap<String, String>();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] kv = pair.split("=");
            res.put(kv[0], kv[1]);
        }
        return res;
    }

    /**
     * Read request body from the input stream
     * @param exchange
     * @return
     * @throws IOException
     */
    public static String fetchRequestBody(HttpExchange exchange) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

}
