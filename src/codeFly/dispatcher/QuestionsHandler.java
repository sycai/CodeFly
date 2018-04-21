package codeFly.dispatcher;

import codeFly.CodeFly;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.nio.file.Files;

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

                int questionNum = CodeFly.repo.getQuestionNum();
                JSONObject jsonObj = new JSONObject();
                JSONArray jsonAry = new JSONArray();
                for (int i = 1; i <= questionNum; i++) {
                    JSONObject jo = new JSONObject();
                    jo.put("qNum", i);
                    jo.put("title", CodeFly.repo.getQuestionTitle(i));
                    jo.put("difficulty", CodeFly.repo.getQuestionDifficulty(i));
                    jsonAry.put(i - 1, jo);
                }
                jsonObj.put("questionList", jsonAry);

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