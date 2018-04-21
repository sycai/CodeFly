package codeFly.dispatcher;

import codeFly.CodeFly;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class QuestionsHandler implements HttpHandler{
    @Override
    public void handle (HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if (method.equalsIgnoreCase("GET")) {
            //get query contained in URI
            String query = exchange.getRequestURI().getQuery();
            if (query == null) {
                File questionListPage = new File(CodeFly.ROOT_DIR + "frontEnd/questions.html");
                exchange.sendResponseHeaders(200, questionListPage.length());
                OutputStream os = exchange.getResponseBody();
                Files.copy(questionListPage.toPath(), os);
                os.close();
                CodeFly.logger.info(String.format("Sending %s to client: %S",
                        questionListPage.getName(), exchange.getRemoteAddress()));
            } else {
                // Need to check cookie first
                List<String> cookies = exchange.getRequestHeaders().get("Cookie");
                boolean userIsAcitve = false;
                for (String c : cookies) {
                    if (c.contains(c)) {
                        // Sanitize cookie
                        int startIdx = c.indexOf(TaskDispatcher.COOKIE_KEY) + TaskDispatcher.COOKIE_KEY.length();
                        int endIdx = c.indexOf(";", startIdx);
                        if (endIdx == -1) {
                            endIdx = c.length();
                        }
                        if (TaskDispatcher.activeUsers.contains(c.substring(startIdx, endIdx))) {
                            userIsAcitve = true;
                            break;
                        }
                    }
                }

                //create a HashMap to store the qnum and questionDescription
                int questionNum = CodeFly.repo.getQuestionNum();
                JSONObject jsonObj = new JSONObject();
                if (userIsAcitve) {
                    jsonObj.put("userActive", true);
                    JSONArray jsonAry = new JSONArray();
                    for (int i = 1; i <= questionNum; i++) {
                        JSONObject jo = new JSONObject();
                        jo.put("qNum", i);
                        jo.put("title", CodeFly.repo.getQuestionTitle(i));
                        jo.put("difficulty", CodeFly.repo.getQuestionDifficulty(i));
                        jsonAry.put(i - 1, jo);
                    }
                    jsonObj.put("questionList", jsonAry);
                } else {
                    jsonObj.put("userActive", false);
                }
                //convert JSONObject to String
                String jsonStr = jsonObj.toString();


                // response with success
                exchange.sendResponseHeaders(200, jsonStr.length());
                exchange.getResponseBody().write(jsonStr.getBytes());
                exchange.close();
            }

        } else {
            // Doesn't support other methods
            HandlerTools.send404NotFound(exchange);
        }
    }

}