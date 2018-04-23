package codeFly.dispatcher;

import codeFly.CodeFly;
import codeFly.tester.JavaTestEngine;
import codeFly.tester.TestResult;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.util.Map;

public class EditorPageHandler implements HttpHandler{
    @Override
    public void handle (HttpExchange exchange) {
        String userName = HandlerTools.getUserName(exchange);

        String method = exchange.getRequestMethod();
        Map<String, String> queryPairs = HandlerTools.parseUriQuery(exchange.getRequestURI().getQuery());
        try {
            if (method.equalsIgnoreCase("GET")) {
                if (queryPairs.containsKey("dscrpajax")) {
                    // This is a ajax request for question description

                    int qNum = Integer.parseInt(queryPairs.get("qnum"));
                    // Fetch description from file system
                    String qDesc = CodeFly.repo.getQuestionDescription(qNum).replaceAll("\n", "<br><br>");
                    String qTitle = CodeFly.repo.getQuestionTitle(qNum);

                    // Build JSON object
                    JSONObject qJson = new JSONObject();
                    qJson.put("qnum", qNum);
                    qJson.put("qdescription", qDesc);
                    qJson.put("qtitle", qTitle);
                    qJson.put("username", userName);


                    // Send back
                    exchange.sendResponseHeaders(200, qJson.toString().length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(qJson.toString().getBytes());
                    os.close();
                    CodeFly.logger.info(String.format("Sending question description to client: %S",
                            exchange.getRemoteAddress()));
                } else {
                    File editorPage = new File(CodeFly.ROOT_DIR + "frontEnd/editor.html");
                    // response with a success response
                    exchange.sendResponseHeaders(200, editorPage.length());
                    OutputStream os = exchange.getResponseBody();
                    Files.copy(editorPage.toPath(), os);
                    os.close();
                    CodeFly.logger.info(String.format("Sending %s to client: %S",
                            editorPage.getName(), exchange.getRemoteAddress()));
                }
            } else if (method.equalsIgnoreCase("POST")) {
                JSONObject testResultJson = new JSONObject();

                // Parse request contents
                String code = HandlerTools.fetchRequestBody(exchange).replace("\\n", "\n");
                // Update user code
                int questionNumber = Integer.parseInt(queryPairs.get("qnum"));
                CodeFly.repo.writeUserCode(questionNumber, userName, code);

                // Test user code
                TestResult testResult = JavaTestEngine.getTestResult(questionNumber, userName);

                // Transform test results into json

                testResultJson.put("isPassed", testResult.isPassed());
                testResultJson.put("testCasesPassed", testResult.getTotalPassedNumber());
                testResultJson.put("testCasesTotal", testResult.getTotalTestsNumber());
                testResultJson.put("stdout", testResult.getStdOut());
                testResultJson.put("stderr", testResult.getStdErr());

                String jsonStr = testResultJson.toString();

                // Send json back
                exchange.sendResponseHeaders(200, jsonStr.length());
                exchange.getResponseBody().write(jsonStr.getBytes());
                exchange.getResponseBody().close();

            } else {
                // All other methods are invalid
                HandlerTools.send404NotFound(exchange);
            }
        } catch (IOException ex) {
            CodeFly.logger.severe(ex.getCause().toString());
        }
    }
}
