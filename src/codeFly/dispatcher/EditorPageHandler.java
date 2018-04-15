package codeFly.dispatcher;

import codeFly.CodeFly;
import codeFly.tester.JavaTestEngine;
import codeFly.tester.TestResult;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.file.Files;
import java.util.Map;

public class EditorPageHandler implements HttpHandler{
    @Override
    public void handle (HttpExchange exchange) {
        // FIXME: request should provide the question number and server should return the proper question description
        String method = exchange.getRequestMethod();
        try {
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
                // Parse request contents
                Map<String, String> queryPairs = HandlerTools.parseUriQuery(exchange.getRequestURI().getQuery());
                String code = HandlerTools.fetchRequestBody(exchange);
                String userName = queryPairs.get("username");

                // Update user code
                int questionNumber = Integer.parseInt(queryPairs.get("qNum"));
                CodeFly.repo.writeUserCode(questionNumber, userName, code);

                // Test user code
                TestResult testResult = JavaTestEngine.getTestResult(questionNumber, userName);



            } else {
                // All other methods are invalid
                HandlerTools.send404NotFound(exchange);
            }
        } catch (IOException ex) {
            CodeFly.logger.severe(ex.getCause().toString());
        }
    }
}
