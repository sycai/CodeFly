package codeFly.dispatcher;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import codeFly.CodeFly;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * A place holder for module that have not been implemented.
 */
public class LoginHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Fetch request method
    	
    	String methodType = exchange.getRequestMethod();
    	
    	// GET method
    	if(methodType.equals("GET")) {
    		  // FIXME: request should provide the question number and server should return the proper question description
            File loginPage = new File(CodeFly.ROOT_DIR + "frontEnd/login.html");
            // response with a success response
            exchange.sendResponseHeaders(200, loginPage.length());
            OutputStream os = exchange.getResponseBody();
            Files.copy(loginPage.toPath(), os);
            os.close();
            CodeFly.logger.info(String.format("Sending %s to client: %S",
                    loginPage.getName(), exchange.getRemoteAddress()));
    	}
    	
    	// POST method
    	else {
    		//Parse the request
            BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            String line;
            ArrayList<String> loginInfo = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
               String[] tokens = line.split(":");
               loginInfo.add(tokens[1]);
            }
            
    		//Comparison with user data in file system's database
            String response ="";
            HashMap<String, String> loginInDb = CodeFly.fileSys.getLoginInfo();
            if (loginInDb.containsKey(loginInfo.get(0))) {
            	//System.out.println("user exist\n");
            	String key = loginInfo.get(0);
            	String value = loginInfo.get(1);
                if(loginInDb.get(key).equals(value)) {
                	//System.out.println("password correct\n");
                	response = "Success";
                }
                else {
                	//System.out.println("password incorrect\n");
                	response = "Fail";
                }
                
            }
            else {
            	//throw new IOException("User " + loginInfo.get(0) + " does not exist.");
            	//System.out.println("user does not exist\n");
            	response = "Fail";
            }
            
    		//sendBack response
    		exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
            CodeFly.logger.info(String.format("Sending Login result to client: %S",exchange.getRemoteAddress()));
    	}

    }
}
