package codeFly.dispatcher;

import codeFly.CodeFly;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.file.Files;

public class EditorPageHandler implements HttpHandler{
    @Override
    public void handle (HttpExchange exchange) throws IOException{
        // FIXME: request should provide the question number and server should return the proper question description
        String method = exchange.getRequestMethod();
        if (method.equalsIgnoreCase("GET")) {
            File editorPage = new File(CodeFly.ROOT_DIR + "frontEnd/editor.html");
            // response with a success response
            exchange.sendResponseHeaders(200, editorPage.length());
            OutputStream os = exchange.getResponseBody();
            Files.copy(editorPage.toPath(), os);
            os.close();
            CodeFly.logger.info(String.format("Sending %s to client: %S",
                    editorPage.getName(), exchange.getRemoteAddress()));
        } else if (method.equalsIgnoreCase("POST")) {
            String query = exchange.getRequestURI().getQuery();

            BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            String body = "";
            String line;
            while ((line = reader.readLine()) != null) {
                body += line;
            }
            System.out.println(body);

            String[] pairs = query.split("&");

            exchange.sendResponseHeaders(200, query.length());
            OutputStream os = exchange.getResponseBody();
            os.write(query.getBytes());
            os.close();
        }
    }
}
