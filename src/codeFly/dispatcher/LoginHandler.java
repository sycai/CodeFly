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
import java.util.Map;

/**
 * Accept "GET/POST" request for login context, GET for users to access the Login view, POST for users to input user name and password to login
 * to his own question list view
 */
public class LoginHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Fetch request method
    	
    	String methodType = exchange.getRequestMethod();
    	
    	// GET method
    	if(methodType.equals("GET")) {
            File loginPage = new File(CodeFly.ROOT_DIR + "frontEnd/login.html");
            // response with a html file of the login view
            exchange.sendResponseHeaders(200, loginPage.length());
            OutputStream os = exchange.getResponseBody();
            Files.copy(loginPage.toPath(), os);
            os.close();
            CodeFly.logger.info(String.format("Sending %s to client: %S",
                    loginPage.getName(), exchange.getRemoteAddress()));
    	}
    	
    	// POST method
    	else if(methodType.equals("POST")){
    		//Parse the request
            BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
            String line;
            String username = ""; 
            String password = "";
            // Get username and password from the parse result
            while ((line = reader.readLine()) != null) {
            	String[] tokens = line.split("&");
            	String[] user_tokens = tokens[0].split("=");
            	String[] pw_tokens = tokens[1].split("=");
            	if(user_tokens[0].equals("username")) {
             	   username = user_tokens[1];
                }
                if(pw_tokens[0].equals("password")) {
             	   password = pw_tokens[1];
                }
            }
    		//Comparison with user data in file system's database and send back different web pages based on the comparison result
            Map<String, String> loginInDb = CodeFly.repo.getLoginInfo();
            if (loginInDb.containsKey(username)) {
            	System.out.println("user exist\n");
                if(loginInDb.get(username).equals(password)) {
                	//user exists, password correct
                	System.out.println("password correct\n");
                	File Sucess_Login_Page = new File(CodeFly.ROOT_DIR + "frontEnd/questions.html");
                    exchange.sendResponseHeaders(200, Sucess_Login_Page.length());
                    OutputStream os = exchange.getResponseBody();
                    Files.copy(Sucess_Login_Page.toPath(), os);
                    os.close();
                    CodeFly.logger.info(String.format("Sending %s to client: %S",
                    		Sucess_Login_Page.getName(), exchange.getRemoteAddress()));
                }
                else {
                	//user exists, password incorrect
                	System.out.println("password incorrect\n");
                	File Error_PW_Page = new File(CodeFly.ROOT_DIR + "frontEnd/password_error.html");
                    exchange.sendResponseHeaders(200, Error_PW_Page.length());
                    OutputStream os = exchange.getResponseBody();
                    Files.copy(Error_PW_Page.toPath(), os);
                    os.close();
                    CodeFly.logger.info(String.format("Sending %s to client: %S",
                    		Error_PW_Page.getName(), exchange.getRemoteAddress()));
                }
                
            }
            else {
            	//user not exist
            	System.out.println("user does not exist\n");
            	File NonUserPage = new File(CodeFly.ROOT_DIR + "frontEnd/username_error.html");
                exchange.sendResponseHeaders(200, NonUserPage.length());
                OutputStream os = exchange.getResponseBody();
                Files.copy(NonUserPage.toPath(), os);
                os.close();
                CodeFly.logger.info(String.format("Sending %s to client: %S",
                		NonUserPage.getName(), exchange.getRemoteAddress()));
            }

    	}

    }
}
