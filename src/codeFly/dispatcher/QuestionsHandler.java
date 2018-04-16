package codeFly.dispatcher;

import codeFly.CodeFly;
import codeFly.fileSystem.Repository;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import java.lang.System;


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
                //create a HashMap to store the qnum and questionDescription
                //HashMap<Integer, String> questionList = new HashMap<>();
                int questionNum = CodeFly.repo.getQuestionNum();
                // for (int i = 1; i <= questionNum; i++) {
                //questionList.put(i, CodeFly.repo.getQuestionDescription(i));
                //}
                //convert HashMap to JSONObject
                //JSONObject jsonObj = new JSONObject(questionList);
                JSONObject jsonObj = new JSONObject();
                JSONArray jsonAry = new JSONArray();
                for(int i=1;i<=questionNum;i++){
                    JSONObject jo=new JSONObject();
                    jo.put("qNum", i);
                    jo.put("title",CodeFly.repo.getQuestionDescription(i));
                    jsonAry.put(i-1,jo);
                }
                jsonObj.put("questionlist",jsonAry);
                //convert JSONObject to String
                String jsonStr = jsonObj.toString();


                // response with a success response
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