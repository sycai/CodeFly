package codeFly.dispatcher;

import codeFly.CodeFly;
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

public class QuestionsHandler implements HttpHandler{
    @Override
    public void handle (HttpExchange exchange) throws IOException{
        // version 1: need the front-end team to provide the questions.html  
    	
        File questionListPage = new File(CodeFly.ROOT_DIR + "frontEnd/questions.html");
        // response with a success response
        exchange.sendResponseHeaders(200, questionListPage.length());
        OutputStream os = exchange.getResponseBody();
        Files.copy(questionListPage.toPath(), os);
        os.close();
        CodeFly.logger.info(String.format("Sending %s to client: %S",
        		questionListPage.getName(), exchange.getRemoteAddress()));
        
        //version 2: need a function in file system: HashMap<Integer, String> getQuestionList()
        
//        String response = "";
//        HashMap<Integer, String> questionList = CodeFly.fileSys.getQuestionList();
//        for (Integer key : questionList.keySet()) {
//            response += key.toString() + ": " + questionList.get(key)+ "\n";
//        }
//        
//        exchange.sendResponseHeaders(200, response.length());
//        OutputStream os = exchange.getResponseBody();
//        os.write(response.getBytes());
//        os.close();
//        CodeFly.logger.info(String.format("Sending QuestionList to client: %S",exchange.getRemoteAddress()));
    }
}
